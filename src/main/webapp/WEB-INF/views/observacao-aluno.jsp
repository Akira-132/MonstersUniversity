<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="com.example.models.Observacao" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    Aluno alunoAtual = (Aluno) request.getAttribute("alunoAtual");
    Observacao observacao = (Observacao) request.getAttribute("observacao");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/obsDiogo.css" />
    <title>Monsters University</title>
</head>
<body>
<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/turma-read" class="ativo">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
            Disciplinas
        </a>
    </nav>

    <div id="info-usuario">
        <div id="avatar">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
        </div>
        <span>
        <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Professor" %></strong>
        Professor
      </span>
    </div>
</aside>

<main>
    <header>Observação do Aluno</header>

    <div id="conteudo">
        <div id="topo">
            <a href="${pageContext.request.contextPath}<%= (alunoAtual != null) ? "/observacao-read?idAluno=" + alunoAtual.getId() : "/turma-read" %>">                <img src="${pageContext.request.contextPath}/assets/imgs/icone-voltar.png" alt="" width="50">
            </a>
            <h1><%= (alunoAtual != null && alunoAtual.getUsuario() != null) ? alunoAtual.getUsuario().getNome() + " " + alunoAtual.getUsuario().getSobrenome() : "Aluno" %></h1>
        </div>

        <form action="${pageContext.request.contextPath}/observacao-update" method="post">

            <input type="hidden" name="id" value="<%= (observacao != null) ? observacao.getId() : "" %>">
            <input type="hidden" name="fkAlunoId" value="<%= (alunoAtual != null) ? alunoAtual.getId() : "" %>">
            <input type="hidden" name="fkProfessorId" value="<%= (usuarioLogado != null) ? usuarioLogado.getId() : "" %>">

            <textarea name="texto" minlength="10" required><%= (observacao != null) ? observacao.getComentario() : "" %></textarea>

            <button type="submit" id="btn-enviar">Salvar Alterações</button>
        </form>
    </div>
</main>
</body>
</html>