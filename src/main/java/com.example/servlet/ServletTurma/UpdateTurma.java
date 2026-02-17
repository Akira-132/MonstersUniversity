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
        TurmaDAO turmaDAO = new TurmaDAO();

        String idStr = request.getParameter("id");
        String periodo = request.getParameter("periodo");
        String sala = request.getParameter("sala");
        String fkDisciplinaIdStr = request.getParameter("fkDisciplinaId");

        boolean success = false;
        String erro = null;
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
            Turma turmaAtual = turmaDAO.readById(id);

            if (turmaAtual != null) {
                turmaAtual.setPeriodo(periodo);
                turmaAtual.setSala(sala);
                turmaAtual.setFkDisciplinaId(Integer.parseInt(fkDisciplinaIdStr));
                success = turmaDAO.update(turmaAtual) > 0;
            } else {
                erro = "Turma não encontrada.";
            }
        } catch (Exception e) {
            erro = e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/turma-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");
        try {
            request.setAttribute("listaTurmas", turmaDAO.read());
            request.setAttribute("listaDisciplinas", new DisciplinaDAO().read());
            if (id > 0) request.setAttribute("turmaModal", turmaDAO.readById(id));
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/turmas.jsp").forward(request, response);
    }
}