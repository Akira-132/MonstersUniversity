package com.example.servlet.ServletTurma;

import java.io.IOException;
import java.util.List;
import com.example.models.Turma;
import com.example.dao.TurmaDAO;
import com.example.dao.DisciplinaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
            List<Turma> lista = turmaDAO.read();
            request.setAttribute("listaTurmas", lista);

            if ("prepararCreate".equals(acao) || "prepararUpdate".equals(acao)) {
                request.setAttribute("listaDisciplinas", disciplinaDAO.read());
            }

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Turma turma = turmaDAO.readById(id);

                if (turma != null) {
                    request.setAttribute("turmaModal", turma);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar dados.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/turmas.jsp").forward(request, response);
    }
}