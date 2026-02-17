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

@WebServlet("/turma-read")
public class ReadTurma extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TurmaDAO turmaDAO = new TurmaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            request.setAttribute("listaTurmas", turmaDAO.read());
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (("prepararUpdate".equals(acao) || "prepararDelete".equals(acao)) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Turma t = turmaDAO.readById(id);
                if (t != null) {
                    t.setDisciplina(disciplinaDAO.readById(t.getFkDisciplinaId()));
                    request.setAttribute("turmaModal", t);
                    request.setAttribute("modalAtivo", "prepararUpdate".equals(acao) ? "update" : "delete");
                }
            }
        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/turmas.jsp").forward(request, response);
    }
}