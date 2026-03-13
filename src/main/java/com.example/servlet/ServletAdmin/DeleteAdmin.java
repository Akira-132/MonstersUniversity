package com.example.servlet.ServletAdmin;

import com.example.dao.AdminDAO;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.models.Admin;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin-delete")
public class DeleteAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        AdminDAO adminDAO = new AdminDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Usuario adminLogado = (Usuario) session.getAttribute("adminLogado");
            Admin adminSessao = adminDAO.readByUsuarioId(adminLogado.getId());
            boolean autoExclusao = (adminSessao != null && adminSessao.getId() == id);

            if (adminDAO.deleteById(id) > 0) {
                if (autoExclusao) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/login-admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin-read");
                }
                return;
            } else {
                erro = "Não foi possível remover o registro. Tente novamente.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Este administrador possui vínculos no sistema.";
            } else {
                erro = "Erro inesperado ao excluir administrador.";
            }
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaAdmins", adminDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("adminModal", adminDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/admin-read").forward(request, response);
    }
}