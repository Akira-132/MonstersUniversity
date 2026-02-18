package com.example.servlet.ServletAdmin;

import java.io.IOException;
import java.util.List;
import com.example.models.Admin;
import com.example.dao.AdminDAO;
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
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Admin> lista = adminDAO.read();
            request.setAttribute("listaAdmins", lista);

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Admin adminSelecionado = adminDAO.readById(id);

                if (adminSelecionado != null) {
                    request.setAttribute("adminModal", adminSelecionado);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar dados do sistema.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}