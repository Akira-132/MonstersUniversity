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

@WebServlet("/turma-delete")
public class DeleteTurma extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TurmaDAO dao = new TurmaDAO();
        String idStr = request.getParameter("id");
        boolean success = false;
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            success = dao.deleteById(id) > 0;
        } catch (Exception e) {
            erro = e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/turma-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaTurmas", dao.read());
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Turma t = dao.readById(id);
                if(t != null) {
                    t.setDisciplina(new DisciplinaDAO().readById(t.getFkDisciplinaId()));
                    request.setAttribute("turmaModal", t);
                }
            }
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/turmas.jsp").forward(request, response);
    }
}