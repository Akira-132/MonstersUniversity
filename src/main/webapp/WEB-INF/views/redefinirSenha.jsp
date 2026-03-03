<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Criar Senha - Monsters University</title>


    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/styles/redefinirSenha.css">

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
    <img src="<%=request.getContextPath()%>/assets/imgs/Logo.png" width="120">
</header>

<div id="fundo">

    <div id="container">
        <img src="<%=request.getContextPath()%>/assets/imgs/crianca_veri.png"
             alt="Personagem Esquerda"
             style="height: 320px; align-self: flex-end;">

        <div id="login-box">

            <h1>Crie uma Senha</h1>

            <% if (request.getAttribute("erro") != null) { %>
            <p style="color:red;"><%= request.getAttribute("erro") %></p>
            <% } %>

            <form action="<%=request.getContextPath()%>/redefinir-senha"
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
                    <a href="<%=request.getContextPath()%>/login" id="btn-voltar">
                        Voltar
                    </a>

                    <input type="submit"
                           value="Entrar"
                           id="btn-login">
                </div>

            </form>
        </div>

        <img src="<%=request.getContextPath()%>/assets/imgs/mike_pequeno.png"
             alt="Personagem Direita"
             style="height: 280px; align-self: flex-end;">

    </div>

</div>

</body>
</html>