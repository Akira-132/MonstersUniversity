package com.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if (uri.contains("/css/") || uri.contains("/img/") || uri.contains("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        if (uri.endsWith("login.jsp") || uri.endsWith("/login") || uri.endsWith("login-admin.jsp") ||
                uri.endsWith("verificacao-aluno.jsp") || uri.endsWith("/verificar-cpf") ||
                uri.endsWith("redefinir-senha.jsp") || uri.endsWith("/esqueci-senha") ||
                uri.endsWith("/logout")) {

            chain.doFilter(request, response);
            return;
        }

        if (uri.endsWith("matricula.jsp") || uri.endsWith("/ativar-matricula")) {
            if (session != null && session.getAttribute("alunoAtivacao") != null) {
                chain.doFilter(request, response); // CPF validado, pode passar!
            } else {
                res.sendRedirect(req.getContextPath() + "/verificacao-aluno.jsp"); // Chuta de volta
            }
            return;
        }

        if (uri.endsWith("verificacao.jsp") || uri.endsWith("/verificar-codigo")) {
            if (session != null && session.getAttribute("emailRecuperacao") != null) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/redefinir-senha.jsp");
            }
            return;
        }

        if (uri.endsWith("criar-senha.jsp") || uri.endsWith("/redefinir-senha")) {
            if (session != null && session.getAttribute("codigoVerificado") != null) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login.jsp");
            }
            return;
        }

        boolean logado = (session != null && session.getAttribute("usuarioLogado") != null);
        if (logado) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}