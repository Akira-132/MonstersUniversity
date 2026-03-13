<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="com.example.models.Observacao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
Aluno alunoAtual = (Aluno) request.getAttribute("alunoAtual");
List<Observacao> listaObservacoes = (List<Observacao>) request.getAttribute("listaObservacoes");
String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
  %>
  <!DOCTYPE html>
  <html lang="pt-BR">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/historico.css" />
    <title>Histórico - Monsters University</title>
  </head>
  <body>
  <aside>
    <div id="logo">
      <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
    </div>

    <nav>
      <a href="${pageContext.request.contextPath}/turma-read" class="ativo">
        <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
        Disciplina
      </a>
      <a href="${pageContext.request.contextPath}/dashboard">
        <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
        Dashboards
      </a>
    </nav>

    <<div id="info-usuario"
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
        <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Professor" %></strong>
        Professor
      </span>
    </div>
  </aside>

  <main>
    <header>Histórico de Observações</header>

    <div id="conteudo">
      <div id="topo">
        <a href="${pageContext.request.contextPath}/aluno-read?id=<%= (alunoAtual != null) ? alunoAtual.getId() : "" %>" id="btn-voltar">
        <img src="${pageContext.request.contextPath}/assets/imgs/icone-voltar.png" alt="" width="50">
        </a>
        <h1><%= (alunoAtual != null && alunoAtual.getUsuario() != null) ? alunoAtual.getUsuario().getNome() + " " + alunoAtual.getUsuario().getSobrenome() : "Nome do Aluno" %></h1>
      </div>

      <div id="historico-lista">
        <%
            if (listaObservacoes != null && !listaObservacoes.isEmpty()) {
                for (Observacao obs : listaObservacoes) {
        %>
        <a href="${pageContext.request.contextPath}/observacao-read?id=<%= obs.getId() %>">
          <div class="historico-item">
            <div class="item-conteudo">
              <p><%= obs.getComentario() %></p>
              <span class="item-data">Enviado em <%= (obs.getDataEnvio() != null) ? obs.getDataEnvio().format(formatter) : "Data Indisponível" %></span>
            </div>
            <div class="item-borda"></div>
          </div>
        </a>
        <%
                }
            } else {
        %>
        <p style="text-align: center; margin-top: 20px;">Nenhuma observação registrada para este aluno.</p>
        <%  } %>
      </div>
    </div>
  </main>

  <script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
  </body>
  </html>