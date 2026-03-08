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