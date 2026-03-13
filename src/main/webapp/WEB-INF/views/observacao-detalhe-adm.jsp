<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="com.example.models.Observacao" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    Aluno alunoAtual = (Aluno) request.getAttribute("alunoAtual");
    Observacao observacao = (Observacao) request.getAttribute("observacao");

    if(usuarioLogado == null){
        response.sendRedirect(request.getContextPath() + "/login-admin");
        return;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
    String idTurmaAtual = request.getAttribute("idTurmaAtual") != null ? String.valueOf(request.getAttribute("idTurmaAtual")) : "";
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/obsDiogo.css"/>
    <title>Observação - Monsters University</title>
</head>

<body>

<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png"/>
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


    <a href="${pageContext.request.contextPath}/perfil-read" id="info-usuario">
        <div id="avatar">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png"/>
        </div>

        <span>
            <strong><%= usuarioLogado.getNome() %></strong>
            Super Administrador
        </span>
    </a>
</aside>

<main>

    <header>Observação do Aluno</header>

    <div id="conteudo">

        <div id="topo">

            <a href="<%= request.getContextPath() %>/observacao-read?idAluno=<%= alunoAtual != null ? alunoAtual.getId() : "" %>&idTurma=<%= idTurmaAtual %>">
                <img src="${pageContext.request.contextPath}/assets/imgs/icone-voltar.png" width="50">
            </a>

            <h1>
                <%= (alunoAtual != null && alunoAtual.getUsuario()!=null)
                        ? alunoAtual.getUsuario().getNome()+" "+alunoAtual.getUsuario().getSobrenome()
                        : "Aluno" %>
            </h1>

        </div>

        <% if(observacao != null){ %>

        <div class="campo">

        </div>

        <div class="campo">

            <label>Observação</label>

            <textarea readonly>
<%= observacao.getComentario() %>
</textarea>

        </div>

        <div style="margin-top:10px;font-size:14px;color:#666;">

            Enviado em
            <strong>
                <%= observacao.getDataEnvio()!=null ? observacao.getDataEnvio().format(formatter) : "Data indisponível" %>
            </strong>

        </div>

        <% } else { %>

        <p>Nenhuma observação encontrada.</p>

        <% } %>

    </div>
</main>

</body>
</html>