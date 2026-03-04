package com.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private final List<String> PUBLIC_PATHS = Arrays.asList(
            "/", "/index.jsp", "/login", "/login-admin", "/login-admin.jsp",
            "/verificacao-aluno.jsp", "/verificar-cpf",
            "/redefinir-senha.jsp", "/esqueci-senha",
            "/logout", "/verificacao-usuario.jsp",
            "/verificacao.jsp", "/redefinir-senha",
            "/verificar-codigo", "/criar-senha",
            "/matricula.jsp", "/ativar-matricula", "/aluno-matricula"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String context = req.getContextPath();
        // caminho relativo à aplicação, ex: "/login" ou "/assets/styles/..."
        String path = uri.substring(context.length());

        HttpSession session = req.getSession(false);

        // 1) liberar assets estáticos (use /assets/ se você guarda tudo lá)
        if (path.startsWith("/assets/") || path.startsWith("/css/") ||
                path.startsWith("/img/") || path.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2) liberar páginas públicas / endpoints públicos
        if (PUBLIC_PATHS.contains(path) || path.startsWith("/public/")) {
            chain.doFilter(request, response);
            return;
        }

        // 3) casos especiais que dependem de sessão (mantidos com path relativo)
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

        // 4) verificação padrão de autenticação
        boolean logado = (session != null && session.getAttribute("usuarioLogado") != null);
        if (logado) {
            chain.doFilter(request, response);
        } else {
            // redireciona para o servlet /login (padrão)
            res.sendRedirect(context + "/login");
        }
    }
}