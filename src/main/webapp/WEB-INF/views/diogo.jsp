<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="com.example.models.Observacao" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    Aluno alunoAtual = (Aluno) request.getAttribute("alunoAtual");
    Observacao observacao = (Observacao) request.getAttribute("observacao");

    if (usuarioLogado == null) {
        response.sendRedirect(request.getContextPath() + "/login");
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
    String observacaoIdVal = (observacao != null) ? String.valueOf(observacao.getId()) : "";
    String textoVal = (observacao != null && observacao.getComentario() != null) ? observacao.getComentario() : "";
    String nomeCompletoAluno = "Aluno";
    if (alunoAtual != null) {
        nomeCompletoAluno = alunoAtual.getUsuario().getNome() + " " + alunoAtual.getUsuario().getSobrenome();
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/obsDiogo.css"/>

    <title>Monsters University</title>
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
        <a href="${pageContext.request.contextPath}/dashboard">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
            Dashboards
        </a>
    </nav>

    <div id="info-usuario" onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'" style="cursor: pointer;">

        <div id="avatar">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png"/>
        </div>

        <span>
<strong><%= usuarioLogado.getNome() %></strong>
Professor
</span>

    </div>

</aside>

<main>

    <header>Criar Observação</header>
    <div id="conteudo">

        <div id="topo">

            <a href="${pageContext.request.contextPath}/turma-aluno-read?id=<%= request.getAttribute("idTurmaAtual") != null ? request.getAttribute("idTurmaAtual") : "" %>">
                <img src="${pageContext.request.contextPath}/assets/imgs/icone-voltar.png" width="50"/>
            </a>

            <h1>
                <%= nomeCompletoAluno %>
            </h1>

            <a href="${pageContext.request.contextPath}/observacao-read?idAluno=<%= alunoIdVal %>" id="btn-historico">
                Ver histórico
            </a>

        </div>

        <%
            String sucesso = request.getParameter("sucesso");
            String erro = request.getParameter("erro");

            String mensagem = null;

            if("observacaoCriada".equals(sucesso)){
                mensagem = "Observação criada com sucesso!";
            }

            if("observacao".equals(erro)){
                mensagem = "Erro ao registrar observação. Sua mensagem excede o limite de 200?";
            }
        %>

        <% if(mensagem != null){ %>

        <script>

            window.addEventListener("load", function(){

                const alertBox = document.createElement("div");

                alertBox.innerText = "<%= mensagem %>";

                alertBox.style.position="fixed";
                alertBox.style.top="20px";
                alertBox.style.left="50%";
                alertBox.style.transform="translateX(-50%)";
                <% if ("observacaoCriada".equals(sucesso)) {%>
                    alertBox.style.background="#E8F0FE";
                    alertBox.style.color="#1a3c7c";
                <% } else { %>
                    alertBox.style.background="#FF4D4D";
                    alertBox.style.color="#FFF";
                <% } %>
                alertBox.style.padding="15px 25px";
                alertBox.style.borderRadius="8px";
                alertBox.style.boxShadow="0 4px 10px rgba(0,0,0,0.15)";
                alertBox.style.fontFamily="Montserrat";
                alertBox.style.fontSize="14px";
                alertBox.style.zIndex="9999";
                alertBox.style.opacity="0";
                alertBox.style.transition="opacity .4s";

                document.body.appendChild(alertBox);

                setTimeout(()=>{
                    alertBox.style.opacity="1";
                },100);

                setTimeout(()=>{
                    alertBox.style.opacity="0";
                    setTimeout(()=>alertBox.remove(),400);
                },4000);

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