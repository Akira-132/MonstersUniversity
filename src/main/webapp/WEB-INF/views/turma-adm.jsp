<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Turma" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    Turma turmaAtual = (Turma) request.getAttribute("turmaAtual");
    List<Aluno> alunosMatriculados = (List<Aluno>) request.getAttribute("listaAlunos");
    List<Aluno> listaTodosAlunos = (List<Aluno>) request.getAttribute("listaTodosAlunos");

    String erro = (String) request.getAttribute("erro");

    int idTurmaAtual = (turmaAtual != null) ? turmaAtual.getId() : 0;
    String nomeTurma = (turmaAtual != null) ? turmaAtual.getSala() : "Turma";
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

    <title>Gerenciar Turma - <%= nomeTurma %></title>

    <style>
        #modal-adicionar-aluno:checked ~ #overlay-adicionar-aluno {
            display: flex;
            position: fixed; top: 0; left: 0;
            width: 100vw; height: 100vh;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center; align-items: center; z-index: 1000;
        }
        #overlay-adicionar-aluno { display: none; }

        input[id^="modal-editar-"]:checked + .overlay-dinamico,
        input[id^="modal-excluir-"]:checked + .overlay-dinamico {
            display: flex;
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            background-color: rgba(0,0,0,0.5);
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
        .overlay-dinamico { display:none; }

        .modal { background: white; padding: 20px; border-radius: 8px; width: 90%; max-width: 500px; }
    </style>
</head>

<body>
<input type="checkbox" id="modal-adicionar-aluno" hidden />

<div id="overlay-adicionar-aluno" class="overlay-statico">
    <div class="modal">
        <h2>Matricular Aluno</h2>
        <form action="${pageContext.request.contextPath}/turma-aluno-create" method="post">
            <input type="hidden" name="fkTurmaId" value="<%= idTurmaAtual %>" />

            <div class="campo">
                <label for="select-aluno">Selecione o Aluno</label>
                <select id="select-aluno" name="fkAlunoId" required>
                    <option value="">Escolha um aluno...</option>
                    <%
                        if (listaTodosAlunos != null) {
                            for (Aluno a : listaTodosAlunos) {
                                String nomeAluno = (a.getUsuario() != null)
                                        ? a.getUsuario().getNome() + " " + a.getUsuario().getSobrenome()
                                        : "Aluno Matrícula: " + a.getMatricula();
                    %>
                    <option value="<%= a.getId() %>">
                        <%= nomeAluno %>
                    </option>
                    <%      }
                    }
                    %>
                </select>
            </div>

            <div class="modal-botoes">
                <label for="modal-adicionar-aluno" class="btn-cancelar">Cancelar</label>
                <button type="submit" class="btn-confirmar">Matricular</button>
            </div>
        </form>
    </div>
</div>

<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
    </div>
    <nav>
        <a href="<%= request.getContextPath() %>/turma-read" class="ativo">
            <img src="<%= request.getContextPath() %>/assets/imgs/icone-diciplinas.png" alt="" />
            Disciplina
        </a>
        <a href="<%= request.getContextPath() %>/adicionar-view">
            <img src="<%= request.getContextPath() %>/assets/imgs/icone-adicionar.png" alt="" />
            Adicionar
        </a>
        <a href="<%= request.getContextPath() %>/professor-read">
            <img src="<%= request.getContextPath() %>/assets/imgs/icone-professores.png" alt=""/>
            Professores
        </a>
    </nav>

    <div id="info-usuario" onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'" style="cursor: pointer;">
        <div id="avatar">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
        </div>
        <span>
            <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Admin" %></strong>
            Administração
        </span>
    </div>
</aside>

<main>
    <header>Gerenciar Turma</header>

    <div id="conteudo">

        <% if (erro != null) { %>
        <div style="color: #ff4d4d; text-align: center; margin-bottom: 15px;"><%= erro %></div>
        <% } %>

        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>TURMA <%= nomeTurma %></h1>

            <a href="${pageContext.request.contextPath}/nota-read?idTurma=<%= idTurmaAtual %>">
                <button style="cursor: pointer; padding: 10px 20px; color: black; border: none; border-radius: 5px;">
                    VER NOTAS
                </button>
            </a>

            <label for="modal-adicionar-aluno" id="modal-adicionar-aluno" >
                + Matricular Aluno
            </label>
        </div>

        <div id="alunos-lista">
            <%
                if (alunosMatriculados != null && !alunosMatriculados.isEmpty()) {
                    for (Aluno a : alunosMatriculados) {
                        String nomeDisplay = (a.getUsuario() != null)
                                ? a.getUsuario().getNome() + " " + a.getUsuario().getSobrenome()
                                : "Matrícula: " + a.getMatricula();

                        String modalEditId = "modal-editar-" + a.getId();
                        String modalDeleteId = "modal-excluir-" + a.getId();
            %>

            <div class="aluno-card">

                <a href="${pageContext.request.contextPath}/aluno-read?id=<%= a.getId() %>" style="text-decoration: none; color: inherit; font-weight: bold; flex-grow: 1;">
                    <%= nomeDisplay %>
                </a>

                <div id="modal" style="display: flex; gap: 10px; align-items: center;">
                    <label for="<%= modalEditId %>">
                        <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" alt="Editar" class="icone-acao"  />
                    </label>

                    <label for="<%= modalDeleteId %>">
                        <img src="${pageContext.request.contextPath}/assets/imgs/icone-lixeira.png" alt="Remover" class="icone-acao"  />
                    </label>
                </div>
            </div>

            <!-- Modal Editar Aluno -->
            <input type="checkbox" id="<%= modalEditId %>" hidden />
            <div id="overlay-editar-<%= a.getId() %>" class="overlay-dinamico">
                <div class="modal">
                    <h2>Editar Aluno</h2>
                    <form action="${pageContext.request.contextPath}/aluno-update" method="post">
                        <input type="hidden" name="id" value="<%= a.getId() %>" />

                        <div class="campo">
                            <label>Nome do Aluno</label>
                            <input type="text" value="<%= nomeDisplay %>" disabled style="border:none;" />
                        </div>

                        <div class="campo">
                            <label for="matricula-edit-<%= a.getId() %>">Matrícula</label>
                            <input type="text" id="matricula-edit-<%= a.getId() %>" name="matricula" value="<%= a.getMatricula() %>" />
                        </div>

                        <div class="modal-botoes">
                            <label for="<%= modalEditId %>" class="btn-cancelar">Cancelar</label>
                            <button type="submit" class="btn-confirmar">Confirmar</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Modal Excluir Aluno -->
            <input type="checkbox" id="<%= modalDeleteId %>" hidden />
            <div id="overlay-excluir-<%= a.getId() %>" class="overlay-dinamico">
                <div class="modal">
                    <h2>Remover Aluno da Turma</h2>
                    <form action="${pageContext.request.contextPath}/turma-aluno-delete" method="post">
                        <input type="hidden" name="idTurma" value="<%= idTurmaAtual %>" />
                        <input type="hidden" name="idAluno" value="<%= a.getId() %>" />

                        <div class="campo">
                            <label>Aluno</label>
                            <input type="text" value="<%= nomeDisplay %>" disabled style="border:none;" />
                        </div>

                        <div class="campo">
                            <label>Tem certeza que deseja remover este aluno da turma?</label>
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
            <div style="text-align: center; margin-top: 30px;">
                <p>Nenhum aluno matriculado nesta turma.</p>
                <p style="font-size: 0.9em;">Use o botão "Matricular Aluno" no menu lateral.</p>
            </div>
            <%  } %>
        </div>

    </div>
</main>
</body>
</html>