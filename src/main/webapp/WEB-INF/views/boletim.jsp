<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Boletim" %>
<%@ page import="java.util.List" %>

<%
  Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
  List<Boletim> listaBoletim = (List<Boletim>) request.getAttribute("listaBoletim");
  String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/boletim.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css">
  <title>Monsters University</title>
</head>

<body>
<aside>
  <div id="logo">
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
  </div>
  <nav>
    <a href="${pageContext.request.contextPath}/disciplina-read">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
      Disciplinas
    </a>
    <a href="${pageContext.request.contextPath}/boletim-read" class="ativo">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
      Boletim
    </a>
  </nav>

  <div id="info-usuario" onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'" style="cursor: pointer;">
    <div id="avatar">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
    </div>
    <span>
    <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() + " "
            + usuarioLogado.getSobrenome() : "Aluno" %></strong>
    Aluno
  </span>
  </div>
</aside>

<main>
  <header>Meu Boletim Histórico</header>

  <div id="conteudo">

    <% if (erro != null) { %>
    <div style="color: #ff4d4d; margin-bottom: 15px; text-align: center;"><%= erro %></div>
    <% } %>

    <div id="view-notas">
      <table>
        <thead>
        <tr>
          <th>Disciplina</th>
          <th>Período</th>
          <th>P1</th>
          <th>P2</th>
          <th>Média Final</th>
          <th>Situação</th>
        </tr>
        </thead>
        <tbody>

        <%
          if (listaBoletim != null && !listaBoletim.isEmpty()) {
            for (Boletim b : listaBoletim) {
        %>
        <tr>
          <td><%= b.getDisciplina() %></td>
          <td><%= b.getSemestre() %>º Semestre / <%= b.getAno() %></td>

          <td>
              <span class="nota <%= (b.getMediaP1() != null && b.getMediaP1() < 6.0) ? "baixa" : "" %>">
                  <%= (b.getMediaP1() != null) ? String.format("%.1f", b.getMediaP1()) : "—" %>
              </span>
          </td>
          <td>
              <span class="nota <%= (b.getMediaP2() != null && b.getMediaP2() < 6.0) ? "baixa" : "" %>">
                  <%= (b.getMediaP2() != null) ? String.format("%.1f", b.getMediaP2()) : "—" %>
              </span>
          </td>
          <td>
              <span class="nota <%= (b.getMediaFinal() != null && b.getMediaFinal() < 6.0) ? "baixa" : "" %>">
                  <%= (b.getMediaFinal() != null) ? String.format("%.1f", b.getMediaFinal()) : "—" %>
              </span>
          </td>

          <td><span class="nota <%= "Reprovado".equalsIgnoreCase(b.getSituacao()) ? "baixa" : "" %>"><%= b.getSituacao() %></span></td>
        </tr>
        <%
          }
        } else {
        %>
        <tr>
          <td colspan="6" style="text-align: center; padding: 20px;">Nenhum registro de notas encontrado.</td>
        </tr>
        <% } %>

        </tbody>
      </table>
    </div>
  </div>
</main>
</body>
</html>