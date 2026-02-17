package com.example.servlet.ServletAdmin;

import com.example.dao.AdminDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Admin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-delete")
public class DeleteAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminDAO adminDAO = new AdminDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (adminDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/admin-read");
                return;
            } else {
                erro = "Não foi possível remover o registro.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Este administrador possui vínculos ativos.";
            } else {
                erro = "Erro inesperado ao excluir.";
            }
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Admin> lista = adminDAO.read();
            for (Admin a : lista) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
            request.setAttribute("listaAdmins", lista);

            String idStr = request.getParameter("id");
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Admin a = adminDAO.readById(id);
                if (a != null) {
                    a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                    request.setAttribute("adminModal", a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (erro == null) request.setAttribute("erro", "Erro inesperado ao recarregar a lista.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}