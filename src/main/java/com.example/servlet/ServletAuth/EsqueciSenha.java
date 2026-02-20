package com.example.servlet.ServletAuth;

import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import com.example.servlet.ServletAuth.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Random;

@WebServlet("/esqueci-senha")
public class EsqueciSenha extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            Usuario usuario = usuarioDAO.readByEmail(email);

            if (usuario != null) {
                Random random = new Random();
                int numero = 10000 + random.nextInt(90000);
                String codigo = String.valueOf(numero);

                HttpSession session = request.getSession();
                session.setAttribute("codigoRecuperacao", codigo);
                session.setAttribute("emailRecuperacao", email);

                EmailService.enviarCodigoRecuperacao(email, codigo);

                response.sendRedirect(request.getContextPath() + "/verificacao.jsp");
                return;
            } else {
                request.setAttribute("erro", "E-mail não encontrado no sistema.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao processar a solicitação.");
        }

        request.getRequestDispatcher("/redefinir-senha.jsp").forward(request, response);
    }
}