package com.example.servlet.ServletUsuario;

import com.example.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/usuario-delete")
public class DeleteUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (usuarioDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/usuario-read");
                return;
            } else {
                erro = "Não foi possível excluir o usuário.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Este usuário possui perfil de Aluno, Professor ou Admin vinculado.";
            } else {
                erro = "Erro inesperado ao excluir usuário.";
            }
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaUsuarios", usuarioDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("usuarioModal", usuarioDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/adicionar.jsp").forward(request, response);
    }
}