package com.example.servlet.ServletAdmin;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.example.models.Admin;
import com.example.models.Usuario;
import com.example.dao.AdminDAO;
import com.example.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin-read")
public class ReadAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminDAO adminDAO = new AdminDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Admin> lista = adminDAO.read();
            request.setAttribute("listaAdmins", lista);

            if ("prepararUpdate".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Admin a = adminDAO.readById(id);
                if (a != null) {
                    Usuario u = usuarioDAO.readById(a.getFkUsuarioId());
                    a.setUsuario(u);

                    request.setAttribute("adminModal", a);
                    request.setAttribute("modalAtivo", "update");
                }
            }
            else if ("prepararDelete".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Admin a = adminDAO.readById(id);
                if(a != null) {
                    Usuario u = usuarioDAO.readById(a.getFkUsuarioId());
                    a.setUsuario(u);
                    request.setAttribute("adminModal", a);
                    request.setAttribute("modalAtivo", "delete");
                }
            }
            else if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao processar dados: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}