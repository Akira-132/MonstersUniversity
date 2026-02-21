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
                response.sendRedirect(request.getContextPath() + "/professor-read");
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
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaProfessores", professorDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("professorModal", professorDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/professores.jsp").forward(request, response);
    }
}