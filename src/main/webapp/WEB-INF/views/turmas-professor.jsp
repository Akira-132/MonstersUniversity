<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Turma" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    List<Turma> listaTurmas = (List<Turma>) request.getAttribute("listaTurmas");
    if (listaTurmas == null) {
        listaTurmas = new java.util.ArrayList<>();
    }

    String erro = (String) request.getAttribute("erro");

    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/turmasAdm.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />

    <title>Monsters University - Minhas Turmas</title>

</head>

<body>

<aside>

    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="Logo" />
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
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="Usuário" />
        </div>

        <span>
            <strong>
                <%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Professor" %>
            </strong>
            Professor
        </span>
    </div>

</aside>

<main>

    <header>Minhas Turmas</header>

    <div id="conteudo">

        <% if (erro != null && !erro.isEmpty()) { %>
        <div style="color:#ff4d4d; text-align:center; margin-bottom:10px;">
            <%= erro %>
        </div>
        <% } %>

        <% String sucesso = (String) request.getAttribute("sucesso"); %>
        <% if (sucesso != null) { %>
        <div style="color:#4CAF50; text-align:center; margin-bottom:10px;"><%= sucesso %></div>
        <% } %>

        <h1>TURMAS</h1>

        <div id="turmas-lista">

            <%
                if (!listaTurmas.isEmpty()) {

                    int contador = 0;

                    for (Turma t : listaTurmas) {

                        if (t == null) continue;

                        String corBarra = (contador % 2 == 0) ? "verde" : "roxo";
                        contador++;
            %>
            <div class="turma-card">
                <a href="${pageContext.request.contextPath}/turma-aluno-read?id=<%= t.getId() %>" class="turma-link"
                   style="width: 100%; height: 100%">
                            <span style="padding-left:20px; font-weight:bold;">
                                <%= (t.getSala() != null) ? t.getSala() : "Turma" %>
                            </span>
                        <div class="barra <%= corBarra %>"></div>
                </a>
            </div>



        <%
                }
            } else {
            %>

            <p style="text-align:center; margin-top:30px;">
                Você não possui turmas cadastradas.
            </p>

            <% } %>

        </div>

    </div>

</main>

</body>
</html>