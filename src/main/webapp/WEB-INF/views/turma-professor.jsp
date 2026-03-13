<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Turma" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    Turma turmaAtual = (Turma) request.getAttribute("turmaAtual");
    List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");

    String nomeTurma = (turmaAtual != null)
            ? turmaAtual.getSala() + " - " + turmaAtual.getPeriodo()
            : "Turma não identificada";
    int idTurma = (turmaAtual != null) ? turmaAtual.getId() : 0;
    String erro = (String) request.getAttribute("erro");
    String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/turmaA.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <title>Monsters University - <%= nomeTurma %></title>
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
                <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome() : "Professor" %></strong>
                Professor
            </span>
    </div>
</aside>

<main>
    <header>Minha turma</header>
    <div id="conteudo">

        <a href="${pageContext.request.contextPath}/turma-read" id="btn-voltar"
           style="position: absolute">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-voltar.png" width="36"/>
        </a>

        <% if (erro != null) { %>
        <div style="color: #ff4d4d; text-align: center; margin-bottom: 20px;"><%= erro %></div>
        <% } %>

        <h1>TURMA <%= nomeTurma %></h1>

        <% if (turmaAtual != null) { %>
        <a href="${pageContext.request.contextPath}/nota-read?idTurma=<%= idTurma %>">
            <button>NOTAS</button>
        </a>
        <% } %>

        <div id="alunos-lista">
            <%
                if (listaAlunos != null && !listaAlunos.isEmpty()) {
                    for (Aluno a : listaAlunos) {
                        String nomeAluno = (a.getUsuario() != null)
                                ? a.getUsuario().getNome() + " " + a.getUsuario().getSobrenome()
                                : "Aluno Matrícula: " + a.getMatricula();
            %>
            <a href="${pageContext.request.contextPath}/aluno-read?id=<%= a.getId() %>&idTurma=<%= idTurma %>"
               class="aluno-card">
                <%= nomeAluno %>
            </a>
            <%
                }
            } else {
            %>
            <div style="text-align: center; margin-top: 20px;">
                <p>Nenhum aluno matriculado nesta turma ainda.</p>
            </div>
            <%  } %>
        </div>

    </div>
</main>

<script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
</body>

</html>