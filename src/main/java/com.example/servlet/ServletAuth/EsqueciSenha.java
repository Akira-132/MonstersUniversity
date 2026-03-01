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
import java.security.SecureRandom;
import java.util.Random;

@WebServlet("/esqueci-senha")
public class EsqueciSenha extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/redefinirSenhaVeri.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");

        if (email == null || email.isBlank()) {
            request.setAttribute("erro", "Informe um e-mail válido.");
            request.getRequestDispatcher("/WEB-INF/views/redefinirSenhaVeri.jsp")
                    .forward(request, response);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            Usuario usuario = usuarioDAO.readByEmail(email);

            if (usuario != null) {

                SecureRandom random = new SecureRandom();
                int numero = 10000 + random.nextInt(90000);
                String codigo = String.valueOf(numero);

                HttpSession session = request.getSession();
                session.setAttribute("codigoRecuperacao", codigo);
                session.setAttribute("emailRecuperacao", email);

                EmailService.enviarCodigoRecuperacao(email, codigo);

                response.sendRedirect(request.getContextPath() + "/verificar-codigo");
                return;

            } else {
                request.setAttribute("erro", "E-mail não encontrado no sistema.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao processar a solicitação.");
        }

        request.getRequestDispatcher("/WEB-INF/views/redefinirSenhaVeri.jsp")
                .forward(request, response);
    }
}