package com.example.servlet.ServletAuth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/verificar-codigo")
public class VerificadorCodigo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String n1 = request.getParameter("n1");
        String n2 = request.getParameter("n2");
        String n3 = request.getParameter("n3");
        String n4 = request.getParameter("n4");
        String n5 = request.getParameter("n5");

        String codigoDigitado = "";
        if (n1 != null && n2 != null && n3 != null && n4 != null && n5 != null) {
            codigoDigitado = n1 + n2 + n3 + n4 + n5;
        }

        HttpSession session = request.getSession();
        String codigoCorreto = (String) session.getAttribute("codigoRecuperacao");
        String email = (String) session.getAttribute("emailRecuperacao");

        if (codigoCorreto == null || email == null) {
            request.setAttribute("erro", "Sessão expirada. Solicite o código novamente.");
            request.getRequestDispatcher("/redefinir-senha.jsp").forward(request, response);
            return;
        }

        if (codigoDigitado.equals(codigoCorreto)) {
            response.sendRedirect(request.getContextPath() + "/criar-senha.jsp");
        } else {
            request.setAttribute("erro", "Código inválido. Tente novamente.");
            request.getRequestDispatcher("/verificacao.jsp").forward(request, response);
        }
    }
}