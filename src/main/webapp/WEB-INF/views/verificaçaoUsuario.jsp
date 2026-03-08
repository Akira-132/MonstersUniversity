<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/verifiçaoUsuario.css">
    <title>Monsters University</title>
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
</header>

<div id="fundo">

    <div id="container">
        <img src="${pageContext.request.contextPath}/assets/imgs/crianca.png" alt="Criança" id="criança" width="230px">
        <div id="login-box">
            <h1>Verificação Aluno</h1>
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
                <form action="${pageContext.request.contextPath}/verificar-cpf" method="post">
                    <input type="text"
                           id="cpf"
                           name="cpf"
                           placeholder="Digite o seu CPF"
                           required
                           maxlength="14"
                           pattern="\d{3}\.?\d{3}\.?\d{3}-?\d{2}"
                           oninput="this.value = this.value.replace(/[^0-9.\-]/g, '')">


                    <div id="buttons">
                        <a href="${pageContext.request.contextPath}/grito" id="btn-voltar">Voltar</a>
                        <input type="submit" value="Entrar" id="btn-login">
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>