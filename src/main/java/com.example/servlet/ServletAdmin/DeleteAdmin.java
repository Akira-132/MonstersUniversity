package com.example.servlet.ServletAdmin;

import com.example.dao.AdminDAO;
import com.example.models.Admin;
import com.example.models.Usuario;
import com.example.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/admin-delete")
public class DeleteAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminDAO dao = new AdminDAO();
        int id = 0;
        boolean success = false;
        String erro = null;

        try {
            String idParam = request.getParameter("id");
            id = Integer.parseInt(idParam);

            int resultado = dao.deleteById(id);

            if (resultado > 0) {
                success = true;
            } else {
                erro = "Não foi possível deletar o administrador.";
            }

        } catch (NumberFormatException e) {
            erro = "ID inválido.";
        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();
        } catch (Exception e) {
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        List<Admin> lista = new ArrayList<>();
        try { lista = dao.read(); } catch (Exception e) {}
        request.setAttribute("listaAdmins", lista);

        if (id > 0) {
            try {
                Admin a = dao.readById(id);
                if (a != null) {
                    UsuarioDAO uDao = new UsuarioDAO();
                    Usuario u = uDao.readById(a.getFkUsuarioId());
                    a.setUsuario(u);
                    request.setAttribute("adminModal", a);
                }
            } catch (Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}