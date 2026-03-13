<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Observacao" %>
<%@ page import="com.example.models.Usuario" %>

<%
  Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
  Observacao observacao = (Observacao) request.getAttribute("observacao");
  String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";
%>


<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="shortcut icon" href="../../assets/imgs/Logo.png" type="image/x-icon" />
  <link rel="stylesheet" href="../../assets/styles/globalApp.css" />    
  <link rel="stylesheet" href="../../assets/styles/diogo.css" />
  <title>Monsters University</title>
</head>

<body>
  <aside>
    <div id="logo">
      <img src="../../assets/imgs/Logo.png" alt="" />
    </div>
    <nav>
      <a href="turmas-professor.jsp" class="ativo">
        <img src="../../assets/imgs/icone-diciplinas.png" alt="" />
        Disciplinas
      </a>
      <a href="${pageContext.request.contextPath}/dashboard">
        <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
        Dashboards
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
            <strong><%= usuarioLogado.getNome() %></strong>
            Perfil
        </span>

    </div>
  </aside>

  <main>
    <header>Minha disciplina</header>
    
    <div id="conteudo">
      <div id="topo">
        <h1>Diogo Martins Nascimento</h1>
        <a href="historico-prof.jsp" id="btn-historico">Ver histórico</a>
      </div>

      <form action="historico-prof.jsp" method="get">
        <input type="text" name="titulo" placeholder="Título" required>
        
        <textarea name="texto" placeholder="Texto sobre o Roberto Dinamite" minlength="100" required></textarea>
        
        <button type="submit" id="btn-enviar">Enviar</button>
      </form>
    </div>
  </main>

  <script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
</body>

</html>