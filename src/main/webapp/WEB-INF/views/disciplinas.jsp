<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Disciplina" %>
<%@ page import="java.util.List" %>

<%
  Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
  List<Disciplina> listaDisciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
  List<Disciplina> disciplinasDoAluno = (List<Disciplina>) request.getAttribute("disciplinasDoAluno");
  String erro = (String) request.getAttribute("erro");
  String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";

  String filtro = request.getParameter("filtro");
  if (filtro == null) filtro = "minhas";

  List<Disciplina> listaExibida = ("todas".equals(filtro) || disciplinasDoAluno == null)
          ? (listaDisciplinas != null ? listaDisciplinas : new java.util.ArrayList<>())
          : disciplinasDoAluno;

  java.util.Set<Integer> idsDoAluno = new java.util.HashSet<>();
  if (disciplinasDoAluno != null) {
    for (Disciplina da : disciplinasDoAluno) {
      idsDoAluno.add(da.getId());
    }
  }
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

  <div id="info-usuario"
       onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'"
       style="cursor: pointer;">
    <div id="avatar">
      <% if (!foto.isEmpty()) { %>
      <img id="avatar-img"
           src="<%= request.getContextPath() + "/assets/imgs/perfil/" + foto %>"
           alt=""
           style="filter: none; width: 100%; height: 100%; object-fit: cover; border-radius: 50%;" />
      <% } else { %>
      <img id="avatar-img"
           src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png"
           alt="" />
      <% } %>
    </div>
    <span>
        <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome() : "Aluno" %></strong>
        Aluno
      </span>
  </div>
</aside>

<main>
  <header>Minhas disciplinas</header>

  <div style="display:flex;gap:10px;padding:20px">
    <a href="?filtro=minhas"
       style="padding:8px 20px;border-radius:20px;font-weight:700;text-decoration:none;
               background:<%= "minhas".equals(filtro) ? "#0d47a1" : "#e0e4ef" %>;
               color:<%= "minhas".equals(filtro) ? "#fff" : "#1a1a2e" %>;">
      Minhas disciplinas
    </a>
    <a href="?filtro=todas"
       style="padding:8px 20px;border-radius:20px;font-weight:700;text-decoration:none;
               background:<%= "todas".equals(filtro) ? "#0d47a1" : "#e0e4ef" %>;
               color:<%= "todas".equals(filtro) ? "#fff" : "#1a1a2e" %>;">
      Todas
    </a>
  </div>

  <div id="conteudo">

    <% if (erro != null) { %>
    <div class="msg-erro"><%= erro %></div>
    <% } %>

      <%
          if (listaExibida != null && !listaExibida.isEmpty()) {
              int count = 0;
              for (Disciplina d : listaExibida) {
                  String corCard = (count % 2 == 0) ? "verde" : "roxo";
                  count++;

                  boolean matriculado = idsDoAluno.contains(d.getId());
      %>

      <% if (matriculado) { %>
      <a class="card <%= corCard %>" href="${pageContext.request.contextPath}/disciplina-detalhe-read?id=<%= d.getId() %>">
          <span><%= d.getNome() %></span>
      </a>
      <% } else { %>
      <div class="card" style="opacity:0.45;cursor:not-allowed;filter:grayscale(1);">
          <span><%= d.getNome() %></span>
          <span style="margin-left:auto;font-size:0.75rem;color:#888;white-space:nowrap;">
    Não matriculado
  </span>
      </div>
      <% } %>

      <%
          }
      } else {
      %>
      <p>Nenhuma disciplina encontrada.</p>
      <%
          }
      %>
  </div>
</main>

<script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
</body>

</html>