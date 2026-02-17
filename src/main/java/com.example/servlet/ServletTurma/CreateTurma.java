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
import java.sql.SQLException;

@WebServlet("/turma-create")
public class CreateTurma extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String periodo = request.getParameter("periodo");
        String sala = request.getParameter("sala");
        String fkDisciplinaIdStr = request.getParameter("fkDisciplinaId");

        TurmaDAO turmaDAO = new TurmaDAO();
        boolean success = false;
        String erro = null;

        try {
            int fkDisciplinaId = Integer.parseInt(fkDisciplinaIdStr);
            Turma novaTurma = new Turma(periodo, sala, fkDisciplinaId);
            success = turmaDAO.create(novaTurma);

            if (!success) erro = "Erro ao cadastrar turma.";

        } catch (Exception e) {
            erro = "Erro: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/turma-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("periodo_previo", periodo);
        request.setAttribute("sala_previo", sala);
        request.setAttribute("fkDisciplinaId_previo", fkDisciplinaIdStr);
        request.setAttribute("modalAtivo", "create");

        try {
            request.setAttribute("listaTurmas", turmaDAO.read());
            request.setAttribute("listaDisciplinas", new DisciplinaDAO().read());
        } catch (SQLException e) {}

        request.getRequestDispatcher("/WEB-INF/pages/turmas.jsp").forward(request, response);
    }
}