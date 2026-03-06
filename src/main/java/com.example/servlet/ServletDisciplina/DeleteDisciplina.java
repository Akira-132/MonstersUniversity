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
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            } else {
                erro = "Não foi possível excluir a disciplina.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao excluir.";
        }

        request.setAttribute("erro", erro);
        request.getRequestDispatcher("/turma-read").forward(request, response);
    }
}