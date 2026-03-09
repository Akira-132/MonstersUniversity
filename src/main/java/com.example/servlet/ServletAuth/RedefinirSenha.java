package com.example.servlet.ServletAuth;

import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/redefinir-senha")
public class RedefinirSenha extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("codigoVerificado") == null) {
            response.sendRedirect(request.getContextPath() + "/esqueci-senha");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/redefinirSenha.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("emailRecuperacao") == null) {
            response.sendRedirect(request.getContextPath() + "/esqueci-senha");
            return;
        }

        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        if (senha == null || confirmarSenha == null) {
            request.setAttribute("erro", "Preencha todos os campos.");
            request.getRequestDispatcher("/WEB-INF/views/redefinirSenha.jsp")
                    .forward(request, response);
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("erro", "As senhas não coincidem.");
            request.getRequestDispatcher("/WEB-INF/views/redefinirSenha.jsp")
                    .forward(request, response);
            return;
        }

        if (senha.length() < 8) {
            request.setAttribute("erro", "A senha deve ter no mínimo 8 caracteres.");
            request.getRequestDispatcher("/WEB-INF/views/redefinirSenha.jsp")
                    .forward(request, response);
            return;
        }

        String email = (String) session.getAttribute("emailRecuperacao");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {

            Usuario usuario = usuarioDAO.readByEmail(email);

            if (usuario != null) {

                usuario.setSenha(senha);

                int linhasAfetadas = usuarioDAO.update(usuario);

                if (linhasAfetadas > 0) {

                    session.invalidate();

                    response.sendRedirect(request.getContextPath() + "/?origem=senha-sucesso");
                    return;
                }
            }

            request.setAttribute("erro", "Erro ao atualizar senha.");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", "Valor de campo inválido: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao salvar nova senha.");
        }
        request.getRequestDispatcher("/WEB-INF/views/redefinirSenha.jsp")
                .forward(request, response);
    }
}