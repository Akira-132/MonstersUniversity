<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/redefinirSenhaVeri.css">
    <title>Recuperação de Senha</title>
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
</header>

<div id="fundo">

    <div id="container">
        <img src="${pageContext.request.contextPath}/assets/imgs/montro_ponte.png"
             alt="Ilustração"
             id="montro_ponte"
             width="250">

        <div id="login-box">
            <h1>Redefinir Senha</h1>

            <%
                String erro = (String) request.getAttribute("erro");
                if (erro != null) {
            %>
            <div style="
                        background-color: #ffe6e6;
                        color: #b00020;
                        border: 1px solid #ffb3b3;
                        padding: 8px;
                        border-radius: 6px;
                        margin-bottom: 12px;
                        text-align: center;
                        font-size: 14px;">
                <%= erro %>
            </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/esqueci-senha" method="post">

                <input type="email"
                       id="email"
                       name="email"
                       placeholder="Email:"
                       required
                       pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
                       title="Digite um email válido">

                <div id="buttons">
                    <a href="${pageContext.request.contextPath}/" id="btn-voltar">Voltar</a>
                    <input type="submit" value="Enviar Código" id="btn-login">
                </div>

            </form>
        </div>
    </div>

</div>
</body>
</html>