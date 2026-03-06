<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Disciplina" %>
<%@ page import="java.util.List" %>

<%
  Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
  Disciplina disciplina = (Disciplina) request.getAttribute("disciplinaAtual");
  String erro = (String) request.getAttribute("erro");

  String nomeDisciplina = (disciplina != null) ? disciplina.getNome() : "Disciplina não encontrada";
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/sustoCriancas.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
  <title>Monsters University - <%= nomeDisciplina %></title>
</head>

<body>
<aside>
  <div id="logo">
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
  </div>
  <nav>
    <a href="${pageContext.request.contextPath}/disciplina-read" class="ativo">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
      Disciplinas
    </a>
    <a href="${pageContext.request.contextPath}/boletim-read">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
      Boletim
    </a>
  </nav>

  <a href="${pageContext.request.contextPath}/perfil-read" id="info-usuario" style="text-decoration: none; color: inherit;">
    <div id="avatar">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
    </div>
    <span>
        <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome() : "Aluno" %></strong>
        Universitário
      </span>
  </a>
</aside>

<main>
  <header>Minhas disciplinas</header>
  <div id="conteudo">

    <% if (erro != null) { %>
    <div style="color: #ff4d4d; margin-bottom: 15px;"><%= erro %></div>
    <% } %>

    <h1 id="disciplina-titulo"><%= nomeDisciplina %></h1>

    <div>
      <div id="card-situacao">
        <p id="descricao">Bem-vindo à disciplina de <%= nomeDisciplina %>. Aqui você aprenderá as melhores técnicas aplicadas na Monsters University.</p>
      </div>
    </div>

  </div>
</main>
</body>
</html>