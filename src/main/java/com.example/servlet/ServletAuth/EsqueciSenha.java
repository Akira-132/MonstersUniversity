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

    private static final SecureRandom RANDOM = new SecureRandom();

    private String gerarCodigo() {
        return String.valueOf(10000 + RANDOM.nextInt(90000));
    }

    private void gerarEEnviarCodigo(HttpSession session, String email) {

        String codigo = gerarCodigo();

        session.setAttribute("codigoRecuperacao", codigo);
        session.setAttribute("emailRecuperacao", email);

        // 🚀 envio assíncrono (não bloqueia)
        EmailService.enviarCodigoRecuperacaoAsync(email, codigo);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("true".equals(request.getParameter("reenviar"))) {

            HttpSession session = request.getSession(false);

            if (session != null) {
                String email = (String) session.getAttribute("emailRecuperacao");

                if (email != null) {
                    gerarEEnviarCodigo(session, email);
                    response.sendRedirect(request.getContextPath() + "/verificar-codigo");
                    return;
                }
            }

            response.sendRedirect(request.getContextPath() + "/esqueci-senha");
            return;
        }

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

                HttpSession session = request.getSession();
                gerarEEnviarCodigo(session, email);

                response.sendRedirect(request.getContextPath() + "/verificar-codigo");
                return;

            } else {
                request.setAttribute("erro", "E-mail não encontrado no sistema.");
            }

        } catch (Exception e) {
            request.setAttribute("erro", "Erro interno ao processar a solicitação.");
        }

        request.getRequestDispatcher("/WEB-INF/views/redefinirSenhaVeri.jsp")
                .forward(request, response);
    }
}