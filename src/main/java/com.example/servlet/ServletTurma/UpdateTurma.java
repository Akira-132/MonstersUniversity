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

@WebServlet("/turma-update")
public class UpdateTurma extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String periodo = request.getParameter("periodo");
        String sala = request.getParameter("sala");
        // String idDisciplinaStr = request.getParameter("fkDisciplinaId");

        TurmaDAO turmaDAO = new TurmaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);

            Turma turma = turmaDAO.readById(id);
            if (turma == null) throw new Exception("Turma não encontrada.");

            turma.setPeriodo(periodo);
            turma.setSala(sala);

            if (turmaDAO.update(turma) > 0) {
                response.sendRedirect(request.getContextPath() + "/turma-read?sucesso=turmaAtualizada");
                return;
            } else {
                erro = "Erro ao atualizar turma no banco.";
            }

        } catch (NumberFormatException e) {
            erro = "Dados inválidos.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");

        try {
            request.setAttribute("listaTurmas", turmaDAO.read());
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());

            if (idStr != null) {
                request.setAttribute("turmaModal", turmaDAO.readById(Integer.parseInt(idStr)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar as listas.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/turmas-adm.jsp").forward(request, response);
    }
}