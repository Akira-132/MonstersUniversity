<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.models.Aluno"%>
<%@ page import="com.example.models.Usuario"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Monsters University - Matrícula</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/matricula.css">
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
    </a>
</header>

<div id="fundo">

    <div id="login-box">
        <h1>Matrícula</h1>

        <%
            String erro = (String) request.getAttribute("erro");
            if (erro != null) {
        %>
        <div style="
                    color: #b00020;
                    background-color: #ffe6e6;
                    border: 1px solid #ffb3b3;
                    padding: 8px;
                    border-radius: 5px;
                    margin-bottom: 10px;
                    font-size: 14px;
                    text-align: center;
                    font-family: 'Montserrat';
                ">
            <%= erro %>
        </div>
        <% } %>

        <%
            Aluno aluno = null;
            Object a = request.getAttribute("aluno");
            if (a instanceof Aluno) {
                aluno = (Aluno) a;
            }

            String nomeCompleto = "";
            String cpfVal = "";
            String matriculaVal = "";
            String idAlunoVal = "";
            String idUsuarioVal = "";

            if (aluno != null) {
                idAlunoVal = String.valueOf(aluno.getId());
                idUsuarioVal = String.valueOf(aluno.getFkUsuarioId());

                cpfVal = aluno.getCpf() != null ? aluno.getCpf() : "";

                if (cpfVal.length() == 11) {
                    cpfVal = cpfVal.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
                }
                matriculaVal = aluno.getMatricula() != null ? aluno.getMatricula() : "";

                Usuario usu = aluno.getUsuario();
                if (usu != null) {
                    String nome = usu.getNome() != null ? usu.getNome() : "";
                    String sobrenome = usu.getSobrenome() != null ? usu.getSobrenome() : "";
                    nomeCompleto = nome + " " + sobrenome;
                }
            }
        %>

        <div>
            <form action="${pageContext.request.contextPath}/aluno-matricula" method="post">

                <input type="hidden" name="idUsuario" value="<%= idUsuarioVal %>">

                <div class="form-grid">

                    <input type="text"
                           id="nome"
                           name="nome"
                           value="<%= nomeCompleto.trim() %>"
                           readonly
                           title="Campo não editável"
                           style="background-color: #E8F0FE; color: #1a1a1a; font-weight: 500; cursor: not-allowed;">

                    <input type="email"
                           id="email"
                           name="email"
                           placeholder="Email:"
                           required
                           pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
                           title="Digite um email válido">

                    <!-- Linha 2 -->
                    <input type="text"
                           id="matricula"
                           name="matricula"
                           value="<%= matriculaVal %>"
                           readonly
                           title="Campo não editável"
                           style="background-color: #E8F0FE; color: #1a1a1a; font-weight: 500; cursor: not-allowed;">

                    <input type="password"
                           id="senha"
                           name="senha"
                           placeholder="Senha"
                           required
                           pattern="^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$"
                           title="A senha deve ter pelo menos 8 caracteres, um número e um caractere especial">


                    <input type="text"
                           id="cpf"
                           name="cpf"
                           value="<%= cpfVal %>"
                           readonly
                           title="Campo não editável"
                           style="background-color: #E8F0FE; color: #1a1a1a; font-weight: 500; cursor: not-allowed;">

                    <input type="submit"
                           value="Enviar"
                           id="btn-enviar">

                </div>
            </form>
        </div>
    </div>

    <img src="${pageContext.request.contextPath}/assets/imgs/montros_escuro.png"
         alt=""
         id="montros_escuro"
         width="360">
</div>
<script>
    document.querySelector("form").addEventListener("submit", function(event) {

        const email = document.getElementById("email").value.trim();
        const senha = document.getElementById("senha").value;

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        let erro = "";

        if (!emailRegex.test(email)) {
            erro = "Digite um email válido.";
        }

        else if (senha.length < 8) {
            erro = "A senha deve ter pelo menos 8 caracteres.";
        }
        else if (!/[0-9]/.test(senha)) {
            erro = "A senha deve conter pelo menos um número.";
        }
        else if (!/[!@#$%^&*]/.test(senha)) {
            erro = "A senha deve conter pelo menos um caractere especial (!@#$%^&*).";
        }

        if (erro !== "") {
            event.preventDefault();

            let erroDiv = document.createElement("div");
            erroDiv.innerHTML = erro;
            erroDiv.style.color = "#b00020";
            erroDiv.style.backgroundColor = "#ffe6e6";
            erroDiv.style.border = "1px solid #ffb3b3";
            erroDiv.style.padding = "8px";
            erroDiv.style.borderRadius = "5px";
            erroDiv.style.marginBottom = "10px";
            erroDiv.style.textAlign = "center";

            const loginBox = document.getElementById("login-box");

            const existente = loginBox.querySelector(".erro-js");
            if (existente) existente.remove();

            erroDiv.classList.add("erro-js");
            loginBox.insertBefore(erroDiv, loginBox.children[1]);
        }
    });
</script>
</body>
</html>