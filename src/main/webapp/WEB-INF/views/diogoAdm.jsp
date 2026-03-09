<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="com.example.models.Observacao" %>

<%
Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
Aluno alunoAtual = (Aluno) request.getAttribute("alunoAtual");
System.out.println("DEBUG alunoAtual: " + alunoAtual);
if(alunoAtual != null){
System.out.println("DEBUG ID aluno: " + alunoAtual.getId());
}

Observacao observacao = (Observacao) request.getAttribute("observacao");

if (usuarioLogado == null) {
response.sendRedirect(request.getContextPath() + "/login-admin");
return;
}

String actionUrl;
if (observacao != null && observacao.getId() > 0) {
actionUrl = request.getContextPath() + "/observacao-update";
} else {
actionUrl = request.getContextPath() + "/observacao-create";
}

String alunoIdVal = (alunoAtual != null) ? String.valueOf(alunoAtual.getId()) : "";
String professorIdVal = String.valueOf(usuarioLogado.getId());
String observacaoIdVal = (observacao != null && observacao.getId() > 0) ? String.valueOf(observacao.getId()) : "";
String textoVal = (observacao != null && observacao.getComentario() != null) ? observacao.getComentario() : "";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/obsDiogo.css" />
  <title>Monsters University - Observação</title>
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
    <a href="${pageContext.request.contextPath}/adicionar-view">
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

  <div id="info-usuario" onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'" style="cursor: pointer;">
    <div id="avatar">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
    </div>
    <span>
            <strong><%= usuarioLogado.getNome() %></strong>
            Professor
        </span>
  </div>
</aside>

<main>
  <header>Minha disciplina</header>

  <div id="conteudo">
    <div id="topo">
      <a href="${pageContext.request.contextPath}/turma-read">
        <img src="${pageContext.request.contextPath}/assets/imgs/icone-voltar.png" alt="voltar" width="50">
      </a>

      <h1>
        <%= alunoAtual.getUsuario().getNome() + " " + alunoAtual.getUsuario().getSobrenome() %>
      </h1>

      <a href="${pageContext.request.contextPath}/observacao-read?idAluno=<%= alunoIdVal %>" id="btn-historico">
        Ver histórico
      </a>
    </div>

    <%
    String sucessoParam = request.getParameter("sucesso");
    String erroParam = request.getParameter("erro");

    String mensagem = null;

    if ("observacaoCriada".equals(sucessoParam)) {
    mensagem = "Observação registrada com sucesso!";
    } else if ("observacao".equals(erroParam)) {
    mensagem = "Erro ao registrar observação.";
    }
    %>

    <% if (mensagem != null) { %>
    <script>
      window.addEventListener("load", function() {
        const alertBox = document.createElement("div");
        alertBox.innerText = "<%= mensagem %>";

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

        setTimeout(() => {
          alertBox.style.opacity = "1";
        }, 100);

        setTimeout(() => {
          alertBox.style.opacity = "0";
          setTimeout(() => alertBox.remove(), 400);
        }, 4000);
      });
    </script>
    <% } %>

    <form action="<%= actionUrl %>" method="post">
    <h3>Escreva uma observação </h3>

    <% if (!observacaoIdVal.isEmpty()) { %>
    <input type="hidden" name="id" value="<%= observacaoIdVal %>">
    <% } %>

    <% if (!alunoIdVal.isEmpty()) { %>
    <input type="hidden" name="fkAlunoId" value="<%= alunoIdVal %>">
    <% } %>

    <div class="campo">
      <textarea id="texto" name="texto" minlength="10" required><%= textoVal %></textarea>
    </div>

    <div class="modal-botoes">
      <button type="submit" id="btn-enviar">Salvar</button>
    </div>

    </form>
  </div>
</main>
</body>
</html>