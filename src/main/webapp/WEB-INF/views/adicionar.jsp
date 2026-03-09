<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    String erro = (String) request.getAttribute("erro");
    String sucesso = (String) session.getAttribute("sucesso");
    if (sucesso != null) {
        session.removeAttribute("sucesso");
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/adicionar.css">

    <title>Monsters University - Adicionar Usuário</title>
</head>
<body>
<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="Logo" />
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/turma-read">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
            Disciplina
        </a>
        <a href="#" class="ativo">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-adicionar.png" alt="" />
            Adicionar
        </a>
        <a href="${pageContext.request.contextPath}/professor-read">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-professores.png" alt=""/>
            Professores
        </a>
        <a href="${pageContext.request.contextPath}/dashboard">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
            Dashboards
        </a>
    </nav>

    <div id="info-usuario">
        <div id="avatar">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
        </div>
        <span>
                <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Admin" %></strong>
                Administração
            </span>
    </div>
</aside>

<main>
    <header>Adicionar Usuário</header>

    <input type="checkbox" id="toggle-tipo">

    <div id="conteudo">
        <h2>Criar usuário</h2>


        <% if (sucesso != null) { %>
        <script>
            window.addEventListener("load", function() {
                const alertBox = document.createElement("div");
                alertBox.innerText = "<%= sucesso %>";

                alertBox.style.position = "fixed";
                alertBox.style.top = "20px";
                alertBox.style.left = "50%";
                alertBox.style.transform = "translateX(-50%)";
                alertBox.style.backgroundColor = "#E8F0FE";
                alertBox.style.color = "#1a3c7c";
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

        <div id="toggle">
            <label id="label-aluno" style="cursor: pointer;" onclick="setTipo('aluno')">Aluno</label>
            <label id="label-professor" style="cursor: pointer;" onclick="setTipo('professor')">Professor</label>
        </div>

        <form action="${pageContext.request.contextPath}/usuario-create" method="post">

            <input type="hidden" name="tipoUsuario" id="input-tipo-usuario" value="aluno">

            <div id="campos">
                <input type="text" name="nome" placeholder="nome" required value="<%= request.getAttribute("nome_previo") != null ? request.getAttribute("nome_previo") : "" %>">

                <input type="text" name="cpf" placeholder="cpf" required maxlength="14"
                       pattern="\d{3}\.?\d{3}\.?\d{3}-?\d{2}"
                       title="CPF no formato 000.000.000-00 ou somente números"
                       oninput="formatarCPF(this)"
                       value="<%= request.getAttribute("cpf_previo") != null ? request.getAttribute("cpf_previo") : "" %>">

                <input type="text" name="sobrenome" placeholder="sobrenome" required value="<%= request.getAttribute("sobrenome_previo") != null ? request.getAttribute("sobrenome_previo") : "" %>">

                <input type="email" name="email" placeholder="email" required value="<%= request.getAttribute("email_previo") != null ? request.getAttribute("email_previo") : "" %>">

                <input type="password" name="senha" placeholder="senha" required>
            </div>

            <button type="submit" id="btn-adicionar">Adicionar</button>
        </form>
    </div>
</main>

<script>
    const checkbox = document.getElementById('toggle-tipo');
    const inputHidden = document.getElementById('input-tipo-usuario');

    function setTipo(tipo) {
        if (tipo === 'professor') {
            checkbox.checked = true;
            inputHidden.value = 'professor';
        } else {
            checkbox.checked = false;
            inputHidden.value = 'aluno';
        }
    }

    checkbox.addEventListener('change', function() {
        inputHidden.value = this.checked ? 'professor' : 'aluno';
    });
</script>
</body>
</html>