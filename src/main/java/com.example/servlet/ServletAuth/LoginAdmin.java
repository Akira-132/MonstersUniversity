package com.example.servlet.ServletAuth;

import com.example.dao.AdminDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Admin;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login-admin")
public class LoginAdmin extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/loginAdm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdminDAO adminDAO = new AdminDAO();

        try {
            Usuario usuario = usuarioDAO.login(username, password);

            if (usuario != null) {
                Admin admin = adminDAO.readByUsuarioId(usuario.getId());

                if (admin != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("adminLogado", usuario);
                    session.setAttribute("usuarioLogado", usuario);
                    session.setAttribute("role", "admin");

                    response.sendRedirect(request.getContextPath() + "/turma-read");
                    return;
                } else {
                    request.setAttribute("erro", "Você não tem permissão de administrador.");
                }

            } else {
                request.setAttribute("erro", "Usuário ou senha inválidos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao tentar fazer login.");
        }

        request.getRequestDispatcher("/WEB-INF/views/loginAdm.jsp").forward(request, response);
    }
}