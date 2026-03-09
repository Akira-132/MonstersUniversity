<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String email = (String) session.getAttribute("emailRecuperacao");
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/verificacao.css">
    <title>Verificação de Código</title>
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
</header>

<div id="fundo">
    <div id="container">
        <div id="login-box">
            <div id="componentes">
                <h1>Verificação</h1>
                <p>
                    Insira o código de 5 dígitos enviado para
                    <strong><%= email != null ? email : "" %></strong>
                </p>
            </div>

            <% if (erro != null) { %>
            <div style="color:#b00020; margin-bottom:10px; text-align:center;">
                <%= erro %>
            </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/verificar-codigo" method="post">
                <div id="inputs" style="display:flex; justify-content:center; gap:12px; margin:20px 0;">

                    <input type="tel" name="n1" maxlength="1" required inputmode="numeric"
                           style="width:65px; height:75px; text-align:center; font-size:34px; font-weight:bold; border-radius:8px; border:2px solid #ccc;">

                    <input type="tel" name="n2" maxlength="1" required inputmode="numeric"
                           style="width:65px; height:75px; text-align:center; font-size:34px; font-weight:bold; border-radius:8px; border:2px solid #ccc;">

                    <input type="tel" name="n3" maxlength="1" required inputmode="numeric"
                           style="width:65px; height:75px; text-align:center; font-size:34px; font-weight:bold; border-radius:8px; border:2px solid #ccc;">

                    <input type="tel" name="n4" maxlength="1" required inputmode="numeric"
                           style="width:65px; height:75px; text-align:center; font-size:34px; font-weight:bold; border-radius:8px; border:2px solid #ccc;">

                    <input type="tel" name="n5" maxlength="1" required inputmode="numeric"
                           style="width:65px; height:75px; text-align:center; font-size:34px; font-weight:bold; border-radius:8px; border:2px solid #ccc;">

                </div>
                <p style="text-align:center; margin-bottom:15px;">
                    Não recebeu o código?
                    <a href="${pageContext.request.contextPath}/esqueci-senha?reenviar=true"
                       style="color:#0056b3; font-weight:bold; text-decoration:none;">
                        Enviar novamente
                    </a>
                </p>

                <div id="buttons">
                    <a href="${pageContext.request.contextPath}/esqueci-senha" id="btn-voltar">Voltar</a>
                    <input type="submit" value="Verificar" id="btn-login">
                </div>
            </form>
        </div>
    </div>

    <img src="${pageContext.request.contextPath}/assets/imgs/jake_fita.png"
         alt="Jake" id="jake_fita" width="280">
</div>
<script>
    const inputs = document.querySelectorAll("#inputs input");

    inputs.forEach((input, index) => {

        input.addEventListener("input", (e) => {

            input.value = input.value.replace(/[^0-9]/g, "");

            if (input.value.length === 1 && index < inputs.length - 1) {
                inputs[index + 1].focus();
            }
        });

        input.addEventListener("keydown", (e) => {
            if (e.key === "Backspace" && input.value === "" && index > 0) {
                inputs[index - 1].focus();
            }
        });

    });
</script>
</body>
</html>