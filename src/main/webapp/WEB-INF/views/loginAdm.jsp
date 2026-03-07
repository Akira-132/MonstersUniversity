<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/loginAdm.css">
    <title>Monsters University - Admin</title>
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/index.jsp">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
    </a>
</header>

<div id="fundo">
    <div id="login-box">
        <h1>Admin</h1>
        <% if (request.getAttribute("erro") != null) { %>
        <div style="
        color: #b00020;
        background-color: #ffe6e6;
        border: 1px solid #ffb3b3;
        padding: 8px;
        border-radius: 5px;
        margin-bottom: 10px;
        font-size: 14px;
        font-family: 'Montserrat';
        text-align: center;">
            <%= request.getAttribute("erro") %>
        </div>
        <% } %>

        <div>
            <form action="${pageContext.request.contextPath}/login-admin" method="post">
                <input type="text" name="username" placeholder="Usuário" required>
                <input type="password" name="password" placeholder="Senha" required>

                <div id="links_principais">
                    <a href="${pageContext.request.contextPath}/esqueci-senha">Esqueceu a Senha?</a>
                </div>

                <input type="submit" value="Entrar" id="btn-login">
            </form>
        </div>
    </div>

    <img src="${pageContext.request.contextPath}/assets/imgs/surpresa.png" alt="Mike" id="img_admin">
</div>
</body>
</html>