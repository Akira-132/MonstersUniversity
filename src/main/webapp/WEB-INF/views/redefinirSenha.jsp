<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Criar Senha - Monsters University</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/redefinirSenha.css">

    <script>
        function validarSenha() {
            const senha = document.getElementById("senha").value;
            const confirmar = document.getElementById("confirmarSenha").value;
            const erro = document.getElementById("erroSenha");

            if (senha !== confirmar) {
                erro.innerText = "As senhas não coincidem.";
                return false;
            }

            if (senha.length < 6) {
                erro.innerText = "A senha deve ter no mínimo 6 caracteres.";
                return false;
            }

            erro.innerText = "";
            return true;
        }
    </script>
</head>

<body>

<header>
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" width="120">
</header>

<div id="fundo">
    <div id="container">
        <img src="${pageContext.request.contextPath}/assets/imgs/crianca_veri.png"
             alt="Personagem Esquerda"
             style="height: 320px; align-self: flex-end;">

        <div id="login-box">
            <h1>Crie uma Senha</h1>

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

            <form action="${pageContext.request.contextPath}/redefinir-senha"
                  method="post"
                  onsubmit="return validarSenha()">

                <input type="password"
                       id="senha"
                       name="senha"
                       placeholder="Crie uma Senha"
                       required
                       pattern="^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$"
                       title="A senha deve ter pelo menos 8 caracteres, um número e um caractere especial">

                <input type="password"
                       id="confirmarSenha"
                       name="confirmarSenha"
                       placeholder="Repita a Senha"
                       required
                       pattern="^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$"
                       title="A senha deve ter pelo menos 8 caracteres, um número e um caractere especial">

                <p id="erroSenha" style="color:red;"></p>

                <div id="buttons">
                    <a href="${pageContext.request.contextPath}/esqueci-senha" id="btn-voltar">Voltar</a>
                    <input type="submit" value="Entrar" id="btn-login">
                </div>
            </form>
        </div>

        <img src="${pageContext.request.contextPath}/assets/imgs/mike_pequeno.png"
             alt="Personagem Direita"
             style="height: 280px; align-self: flex-end;">
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
</body>
</html>