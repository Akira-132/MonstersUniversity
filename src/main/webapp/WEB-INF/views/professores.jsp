<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Professor" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    List<Professor> listaProfessores = (List<Professor>) request.getAttribute("listaProfessores");
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/turmaAdm.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/modal.css">
    <title>Monsters University</title>
</head>

<body>

<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/turma-read">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
            Disciplina
        </a>
        <a href="${pageContext.request.contextPath}/adicionar-view">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-adicionar.png" alt="" />
            Adicionar
        </a>
        <a href="${pageContext.request.contextPath}/professor-read" class="ativo">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-professores.png" alt=""/>
            Professores
        </a>
    </nav>

    <div id="info-usuario">
        <div id="avatar">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
        </div>
        <span>
                <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Admin" %></strong>
                Super Administrador
            </span>
    </div>
</aside>

<main>
    <header>Professores</header>
    <div id="conteudo">

        <% if (erro != null) { %>
        <div style="color: #ff4d4d; text-align: center; margin-bottom: 10px;"><%= erro %></div>
        <% } %>

        <h1>PROFESSORES</h1>

        <div id="professores-lista">
            <%
                if (listaProfessores != null && !listaProfessores.isEmpty()) {
                    for (Professor p : listaProfessores) {
                        String modalEditId = "modal-editar-" + p.getId();
                        String modalDeleteId = "modal-excluir-" + p.getId();
                        String nomeDisplay = (p.getUsuario() != null)
                                ? p.getUsuario().getNome() + " " + p.getUsuario().getSobrenome()
                                : "Professor ID: " + p.getId();
            %>

            <div class="professor-card">
                <%= nomeDisplay %>

                <div style="display: flex; gap: 10px; align-items: center;">
                    <label for="<%= modalEditId %>">
                        <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" alt="Editar" class="icone-acao"  />
                    </label>

                    <label for="<%= modalDeleteId %>">
                        <img src="${pageContext.request.contextPath}/assets/imgs/icone-lixeira.png" alt="Excluir" class="icone-acao" />
                    </label>
                </div>
            </div>

            <!-- Modal Editar Professor -->
            <input type="checkbox" id="<%= modalEditId %>" hidden>
            <div id="overlay-editar-<%= p.getId() %>" class="overlay-dinamico">
                <div class="modal">
                    <h2>Editar Professor</h2>

                    <form action="${pageContext.request.contextPath}/professor-update" method="post">
                        <input type="hidden" name="id" value="<%= p.getId() %>">

                        <div class="campo">
                            <label for="nome-edit-<%= p.getId() %>">Nome</label>
                            <input type="text" id="nome-edit-<%= p.getId() %>" name="nome" value="<%= (p.getUsuario() != null && p.getUsuario().getNome() != null) ? p.getUsuario().getNome() : "" %>" />
                        </div>

                        <div class="campo">
                            <label for="sobrenome-edit-<%= p.getId() %>">Sobrenome</label>
                            <input type="text" id="sobrenome-edit-<%= p.getId() %>" name="sobrenome" value="<%= (p.getUsuario() != null && p.getUsuario().getSobrenome() != null) ? p.getUsuario().getSobrenome() : "" %>" />
                        </div>

                        <div class="modal-botoes">
                            <label for="<%= modalEditId %>" class="btn-cancelar">Cancelar</label>
                            <button type="submit" class="btn-confirmar">Confirmar</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Modal Excluir Professor -->
            <input type="checkbox" id="<%= modalDeleteId %>" hidden>
            <div id="overlay-excluir-<%= p.getId() %>" class="overlay-dinamico">
                <div class="modal">
                    <h2>Excluir Professor</h2>

                    <form action="${pageContext.request.contextPath}/professor-delete" method="post">
                        <input type="hidden" name="id" value="<%= p.getId() %>">

                        <div class="campo">
                            <label>Nome</label>
                            <input type="text" value="<%= nomeDisplay %>" disabled style="border:none;" />
                        </div>

                        <div class="campo">
                            <label>Tem certeza que deseja excluir este Professor?</label>
                        </div>

                        <div class="modal-botoes">
                            <label for="<%= modalDeleteId %>" class="btn-cancelar">Cancelar</label>
                            <button type="submit" class="btn-confirmar">Confirmar</button>
                        </div>
                    </form>
                </div>
            </div>

            <%
                }
            } else {
            %>
            <p style="text-align: center; color: #666; margin-top: 20px;">Nenhum professor cadastrado.</p>
            <% } %>
        </div>

    </div>
</main>

<style>
    input[id^="modal-editar-"]:checked + .overlay-dinamico,
    input[id^="modal-excluir-"]:checked + .overlay-dinamico {
        display: flex;
        position: fixed;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
        background-color: rgba(0, 0, 0, 0.5);
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }
    .overlay-dinamico { display: none; }
</style>
</body>
</html>