package com.example.servlet.ServletAuth;

import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/redefinir-senha")
public class RedefinirSenha extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String novaSenha = request.getParameter("senha");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("emailRecuperacao");

        if (email == null) {
            request.setAttribute("erro", "Sessão expirada. Inicie o processo novamente.");
            request.getRequestDispatcher("/redefinir-senha.jsp").forward(request, response);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            Usuario usuario = usuarioDAO.readByEmail(email);

            if (usuario != null) {
                usuario.setSenha(novaSenha);

                if (usuarioDAO.update(usuario) > 0) {
                    session.removeAttribute("codigoRecuperacao");
                    session.removeAttribute("emailRecuperacao");
                    session.removeAttribute("codigoVerificado");

                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                } else {
                    request.setAttribute("erro", "Erro ao atualizar a senha.");
                }
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", "Validação: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao salvar nova senha.");
        }

        request.getRequestDispatcher("/criar-senha.jsp").forward(request, response);
    }
}