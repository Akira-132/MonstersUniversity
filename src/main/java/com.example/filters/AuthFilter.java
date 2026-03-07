package com.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.DispatcherType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = "/*", dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.ERROR
})
public class AuthFilter implements Filter {

    private final List<String> PUBLIC_PATHS = Arrays.asList(
            "/", "/grito", "/index.jsp", "/login", "/login-admin", "/login-admin.jsp",
            "/verificacao-aluno.jsp", "/verificar-cpf",
            "/redefinir-senha.jsp", "/esqueci-senha",
            "/logout", "/verificacao-usuario.jsp",
            "/verificacao.jsp", "/redefinir-senha",
            "/verificar-codigo", "/criar-senha",
            "/matricula.jsp", "/ativar-matricula", "/aluno-matricula",
            "/erro400.html", "/erro401.html", "/erro403.html", "/erro404.html", "/erro500.html"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String context = req.getContextPath();
        String path = uri.substring(context.length());

        HttpSession session = req.getSession(false);

        if (path.startsWith("/assets/") || path.startsWith("/css/") ||
                path.startsWith("/img/") || path.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        if (PUBLIC_PATHS.contains(path) || path.startsWith("/public/")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.equals("/matricula.jsp") || path.equals("/ativar-matricula")) {
            if (session != null && session.getAttribute("alunoAtivacao") != null) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(context + "/verificacao-aluno.jsp");
            }
            return;
        }

        if (path.equals("/verificacao.jsp") || path.equals("/verificar-codigo")) {
            if (session != null && session.getAttribute("emailRecuperacao") != null) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(context + "/redefinir-senha.jsp");
            }
            return;
        }

        if (path.equals("/criar-senha.jsp") || path.equals("/redefinir-senha")) {
            if (session != null && session.getAttribute("codigoVerificado") != null) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(context + "/login"); // padronizei para /login
            }
            return;
        }

        boolean logado = (session != null && session.getAttribute("usuarioLogado") != null);
        if (logado) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(context + "/login");
        }
    }
}