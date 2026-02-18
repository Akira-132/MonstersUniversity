package com.example.servlet.ServletDisciplina;

import com.example.dao.DisciplinaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/disciplina-delete")
public class DeleteDisciplina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (disciplinaDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/disciplina-read");
                return;
            } else {
                erro = "Não foi possível excluir a disciplina.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Existem turmas ou notas vinculadas a esta disciplina.";
            } else {
                erro = "Erro inesperado ao excluir.";
            }
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("disciplinaModal", disciplinaDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}