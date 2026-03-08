package com.example.servlet.ServletTurma;

import com.example.dao.DisciplinaDAO;
import com.example.dao.TurmaDAO;
import com.example.models.Turma;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/turma-create")
public class CreateTurma extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String periodo = request.getParameter("periodo");
        String sala = request.getParameter("sala");
        String idDisciplinaStr = request.getParameter("fkDisciplinaId");

        TurmaDAO turmaDAO = new TurmaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String erro = null;

        try {
            int fkDisciplinaId = Integer.parseInt(idDisciplinaStr);

            Turma novaTurma = new Turma(periodo, sala, fkDisciplinaId);

            if (turmaDAO.create(novaTurma)) {
                response.sendRedirect(request.getContextPath() + "/turma-read?sucesso=ok");
                return;
            } else {
                erro = "Erro ao cadastrar a turma no banco.";
            }

        } catch (NumberFormatException e) {
            erro = "Selecione uma disciplina válida.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao cadastrar turma.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");
        request.setAttribute("periodo_previo", periodo);
        request.setAttribute("sala_previo", sala);

        try {
            request.setAttribute("listaTurmas", turmaDAO.read());
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro crítico: Não foi possível carregar as listas.");
        }

        request.getRequestDispatcher("/WEB-INF/views/turmas-adm.jsp").forward(request, response);
    }
}