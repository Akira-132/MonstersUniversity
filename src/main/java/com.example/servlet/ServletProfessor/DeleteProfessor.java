package com.example.servlet.ServletProfessor;

import com.example.dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/professor-delete")
public class DeleteProfessor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProfessorDAO professorDAO = new ProfessorDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (professorDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/professor-read?sucesso=professorExcluido");
                return;
            } else {
                erro = "Não foi possível remover o registro.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Este professor possui disciplinas ou observações vinculadas.";
            } else {
                erro = "Erro inesperado ao excluir professor.";
            }
        }

        request.setAttribute("erro", erro);
        request.getRequestDispatcher("/professor-read").forward(request, response);
    }
}