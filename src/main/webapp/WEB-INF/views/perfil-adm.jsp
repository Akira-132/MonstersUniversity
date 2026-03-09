<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Telefone" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    Telefone telefone = (Telefone) request.getAttribute("telefoneDoUsuario");

    String nomeCompleto = (usuarioLogado != null) ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome() : "Admin";
    String email = (usuarioLogado != null) ? usuarioLogado.getEmail() : "";
    String tel = (telefone != null) ? telefone.getTelefone()   : "";

    String sucesso = (String) request.getAttribute("sucesso");
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/perfil.css" />
    <title>Perfil - Monsters University</title>
</head>
<body>

<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/turma-read">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
            Disciplina
        </a>
        <a href="${pageContext.request.contextPath}/adicionar-view">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-adicionar.png" alt="" />
            Adicionar
        </a>
        <a href="${pageContext.request.contextPath}/professor-read">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-professores.png" alt="" />
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
            Super Administrador
        </span>
    </div>
</aside>

<main>
    <header>Meu Perfil</header>

    <div id="conteudo">
        <div id="perfil-container">
            <div id="foto-perfil">
                <img src="${pageContext.request.contextPath}/assets/imgs/mike-perfil.png" alt="Foto de perfil" />
            </div>

            <h1><%= nomeCompleto %></h1>

            <% if (erro != null) { %>
            <div style="color: #ff4d4d; margin-bottom: 10px; text-align: center;"><%= erro %></div>
            <% } %>
            <% if (sucesso != null) { %>
            <div style="color: #4CAF50; margin-bottom: 10px; text-align: center;"><%= sucesso %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/perfil-update" method="post">
                <div id="campos-grid">
                    <div class="campo-perfil">
                        <label for="email">Email</label>
                        <div class="input-editavel">
                            <input type="email" id="email" name="email" value="<%= email %>" />
                            <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" alt="Editar" class="icone-editar-campo" />
                        </div>
                    </div>

                    <div class="campo-perfil">
                        <label for="telefone">Telefone</label>
                        <div class="input-editavel">
                            <input type="tel" id="telefone" name="telefone" value="<%= tel %>" maxlength="15" />
                            <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" alt="Editar" class="icone-editar-campo" />
                        </div>
                    </div>
                </div>

                <div class="campo-perfil campo-descricao">
                    <label for="sobre">Sobre mim</label>
                    <textarea id="sobre" rows="6" readonly style="background-color: #f0f0f0; cursor: default;" disabled>Super Visor dedicado da Monsters University. Membro ativo da equipe de super visão.</textarea>
                </div>

                <button type="submit" id="btn-salvar" style="background-color: #0d47a1; color: white; padding: 12px; border-radius: 8px; width: 100%; font-weight: bold; border: none; cursor: pointer; margin-bottom: 15px;">SALVAR ALTERAÇÕES</button>
            </form>

            <form action="${pageContext.request.contextPath}/logout" method="get" style="width: 100%;">
                <button type="submit" id="btn-sair">SAIR DA CONTA</button>
            </form>
        </div>
    </div>
</main>
</body>
</html>
