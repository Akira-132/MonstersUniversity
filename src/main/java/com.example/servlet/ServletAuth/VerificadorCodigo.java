package com.example.servlet.ServletAuth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/verificar-codigo")
public class VerificadorCodigo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("codigoRecuperacao") == null) {
            response.sendRedirect(request.getContextPath() + "/esqueci-senha");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/verificacao.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String codigoDigitado =
                request.getParameter("n1") +
                        request.getParameter("n2") +
                        request.getParameter("n3") +
                        request.getParameter("n4") +
                        request.getParameter("n5");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/esqueci-senha");
            return;
        }

        String codigoCorreto = (String) session.getAttribute("codigoRecuperacao");

        if (codigoCorreto == null) {
            response.sendRedirect(request.getContextPath() + "/esqueci-senha");
            return;
        }

        if (codigoDigitado.equals(codigoCorreto)) {

            session.removeAttribute("codigoRecuperacao");
            session.setAttribute("codigoVerificado", true);

            response.sendRedirect(request.getContextPath() + "/redefinir-senha");
        } else {
            request.setAttribute("erro", "Código inválido. Tente novamente.");
            request.getRequestDispatcher("/WEB-INF/views/verificacao.jsp")
                    .forward(request, response);
        }
    }
}