<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Admin" %>
<%@ page import="com.example.dao.UsuarioDAO" %>
<%@ page import="com.example.dao.AdminDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String erro = null;

    if(request.getMethod().equalsIgnoreCase("POST")){

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.login(username, password);

        if(usuario != null){

            AdminDAO adminDAO = new AdminDAO();
            Admin admin = adminDAO.readByUsuarioId(usuario.getId());

            if(admin != null){
                session.setAttribute("adminLogado", usuario);
                response.sendRedirect("dashboardAdm.jsp");
                return;
            } else {
                erro = "Você não tem permissão de administrador.";
            }

        } else {
            erro = "Usuário ou senha inválidos.";
        }
    }
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/loginAdm.css">
    <title>Monsters University</title>
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
</header>

<div id="fundo">

    <div id="login-box">
        <h1>Admin</h1>

        <% if(erro != null){ %>
        <p style="color:red; text-align:center;"><%= erro %></p>
        <% } %>

        <div>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="text" name="username" placeholder="Usuário" required>
                <input type="password" name="password" placeholder="Senha" required>

                <div id="links_principais">
                    <a href="redefinirSenhaVeri.jsp">Esqueceu a Senha?</a>
                </div>

                <input type="submit" value="Entrar" id="btn-login">
            </form>
        </div>
    </div>

    <img src="${pageContext.request.contextPath}/assets/imgs/surpresa.png"
         alt="Mike" id="img_admin">
</div>
</body>
</html>