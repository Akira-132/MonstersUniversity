package com.example.servlet.ServletTurma;

import com.example.dao.TurmaDAO;
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

        TurmaDAO turmaDAO = new TurmaDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (turmaDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            } else {
                erro = "Não foi possível excluir a turma.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Existem alunos matriculados nesta turma.";
            } else {
                erro = "Erro inesperado ao excluir.";
            }
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaTurmas", turmaDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("turmaModal", turmaDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/turmas.jsp").forward(request, response);
    }
}