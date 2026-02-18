package com.example.servlet.ServletTelefone;

import com.example.dao.TelefoneDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/telefone-delete")
public class DeleteTelefone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TelefoneDAO telefoneDAO = new TelefoneDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (telefoneDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/telefone-read");
                return;
            } else {
                erro = "Não foi possível excluir o telefone.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao excluir.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaTelefones", telefoneDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("telefoneModal", telefoneDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}