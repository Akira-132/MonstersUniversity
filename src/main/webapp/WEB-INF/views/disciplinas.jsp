<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Disciplina" %>
<%@ page import="java.util.List" %>

<%
  Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
  List<Disciplina> listaDisciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
  String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/diciplinas.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css">

  <title>Monsters University</title>
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

  <div id="info-usuario" onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'" style="cursor: pointer;">
    <div id="avatar">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
    </div>
    <span>
        <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome() : "Aluno" %></strong>
        Turma A
      </span>
  </div>
</aside>

<main>
  <header>Minhas disciplinas</header>

  <div id="conteudo">

    <% if (erro != null) { %>
    <div class="msg-erro"><%= erro %></div>
    <% } %>

    <%
      if (listaDisciplinas != null && !listaDisciplinas.isEmpty()) {
        int count = 0;
        for (Disciplina d : listaDisciplinas) {
          String corCard = (count % 2 == 0) ? "verde" : "roxo";
          count++;
    %>
    <a href="${pageContext.request.contextPath}/disciplina-detalhe-read?id=<%= d.getId() %>">
      <div id="disciplina-<%= d.getId() %>" class="card <%= corCard %>">
        <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
        <span><%= d.getNome() %></span>
      </div>
    </a>
    <%
      }
    } else {
    %>
    <p>Nenhuma disciplina encontrada.</p>
    <% } %>

  </div>
</main>
</body>

</html>