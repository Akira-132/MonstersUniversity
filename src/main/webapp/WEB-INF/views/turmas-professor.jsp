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

    <link rel="shortcut icon" href="<%= contextPath %>/assets/imgs/Logo.png" type="image/x-icon" />

    <link rel="stylesheet" href="<%= contextPath %>/assets/styles/turmasAdm.css" />
    <link rel="stylesheet" href="<%= contextPath %>/assets/styles/globalApp.css" />

    <title>Monsters University - Minhas Turmas</title>

    <style>
        .turma-card {
            cursor: pointer;
        }

        .turma-link {
            text-decoration: none;
            color: inherit;
            flex-grow: 1;
            height: 100%;
            display: flex;
            align-items: center;
        }
    </style>
</head>

<body>

<aside>

    <div id="logo">
        <img src="<%= contextPath %>/assets/imgs/Logo.png" alt="Logo" />
    </div>

    <nav>
        <a href="<%= contextPath %>/turma-read" class="ativo">
            <img src="<%= contextPath %>/assets/imgs/icone-diciplinas.png" alt="" />
            Disciplina
        </a>
    </nav>

    <div id="info-usuario" onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'" style="cursor: pointer;">
        <div id="avatar">
            <img src="<%= contextPath %>/assets/imgs/icone-usuario.png" alt="Usuário" />
        </div>

        <span>
            <strong>
                <%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Professor" %>
            </strong>
            Docente
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

                <a href="<%= contextPath %>/turma-aluno-read?id=<%= t.getId() %>" class="turma-link">

                <span style="padding-left:20px; font-weight:bold;">
                    <%= (t.getSala() != null) ? t.getSala() : "Turma" %>
                </span>

                </a>

                <div class="barra <%= corBarra %>"></div>

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