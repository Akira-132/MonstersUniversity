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

        <% String erro = (String) request.getAttribute("erro"); %>

        <% if (erro != null) { %>
        <script>
            window.addEventListener("load", function() {
                const alertBox = document.createElement("div");
                alertBox.innerText = "<%= erro %>";

                alertBox.style.position = "fixed";
                alertBox.style.top = "20px";
                alertBox.style.left = "50%";
                alertBox.style.transform = "translateX(-50%)";
                alertBox.style.backgroundColor = "#FDE8E8";
                alertBox.style.color = "#7c1a1a";
                alertBox.style.padding = "15px 25px";
                alertBox.style.borderRadius = "8px";
                alertBox.style.boxShadow = "0 4px 10px rgba(0,0,0,0.15)";
                alertBox.style.fontFamily = "Montserrat";
                alertBox.style.fontSize = "14px";
                alertBox.style.zIndex = "9999";
                alertBox.style.opacity = "0";
                alertBox.style.transition = "opacity 0.4s ease";

                document.body.appendChild(alertBox);

                setTimeout(() => { alertBox.style.opacity = "1"; }, 100);
                setTimeout(() => {
                    alertBox.style.opacity = "0";
                    setTimeout(() => alertBox.remove(), 400);
                }, 4000);
            });
        </script>
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

    <img src="${pageContext.request.contextPath}/assets/imgs/surpresa.png" alt="Surpresa" id="img_admin">
</div>

<script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
</body>
</html>