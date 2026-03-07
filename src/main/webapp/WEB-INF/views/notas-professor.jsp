<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Nota" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="com.example.models.Disciplina" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    List<Nota> listaNotas = (List<Nota>) request.getAttribute("listaNotas");
    List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
    List<Disciplina> listaDisciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");

    String modalAtivo = (String) request.getAttribute("modalAtivo");
    Nota notaModal = (Nota) request.getAttribute("notaModal");
    String erro = (String) request.getAttribute("erro");

    String idTurma = request.getParameter("idTurma");
    if(idTurma == null) idTurma = "0";
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/notasProfessor.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css">

    <title>Monsters University</title>
</head>

<body>

<input type="checkbox" id="modal-input" hidden <%= (modalAtivo != null) ? "checked" : "" %>/>

<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png"/>
    </div>

    <nav>
        <a href="${pageContext.request.contextPath}/turma-read" class="ativo">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png"/>
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
<strong>
<%= (usuarioLogado != null)
        ? usuarioLogado.getNome()+" "+usuarioLogado.getSobrenome()
        : "Professor" %>
</strong>
Minha Disciplina
</span>

    </div>
</aside>

<main>

    <header>Minha disciplina</header>

    <div id="conteudo">

        <% if (erro != null) { %>
        <div style="color:#ff4d4d;margin-bottom:15px;text-align:center;">
            <%= erro %>
        </div>
        <% } %>

        <div class="view-notas">

            <table>

                <thead>
                <tr>
                    <th>Aluno</th>
                    <th>Disciplina</th>
                    <th>Avaliação</th>
                    <th>Período</th>
                    <th>Nota</th>
                    <th>Ações</th>
                </tr>
                </thead>

                <tbody>

                <%
                    if (listaNotas != null && !listaNotas.isEmpty()) {
                        for (Nota n : listaNotas) {
                %>

                <tr>

                    <td>
                        <%= n.getAluno().getUsuario().getNome() %>
                        <%= n.getAluno().getUsuario().getSobrenome() %>
                    </td>

                    <td>
                        <%= n.getDisciplina().getNome() %>
                    </td>

                    <td>
                        <%= n.getTipo() %>
                    </td>

                    <td>
                        <%= n.getSemestre() %>º Sem / <%= n.getAno() %>
                    </td>

                    <td>
<span class="nota <%= (n.getNota() < 6.0) ? "baixa" : "" %>">
<%= String.format("%.1f", n.getNota()) %>
</span>
                    </td>

                    <td style="display:flex;gap:10px;justify-content:center;">

                        <a href="${pageContext.request.contextPath}/nota-read?acao=prepararUpdate&id=<%= n.getId() %>&idTurma=<%= idTurma %>">
                            <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" class="icone-editar"/>
                        </a>

                        <form action="${pageContext.request.contextPath}/nota-delete" method="post">
                            <input type="hidden" name="id" value="<%= n.getId() %>"/>
                            <input type="hidden" name="idTurma" value="<%= idTurma %>"/>

                            <input type="image"
                                   src="${pageContext.request.contextPath}/assets/imgs/icone-lixeira.png"
                                   class="icone-lixeira"
                                   onclick="return confirm('Apagar esta nota?');"/>
                        </form>

                    </td>

                </tr>

                <%
                    }
                } else {
                %>

                <tr>
                    <td colspan="6" style="text-align:center;padding:20px;">
                        Nenhuma nota lançada em suas turmas.
                    </td>
                </tr>

                <%
                    }
                %>

                </tbody>
            </table>
        </div>
    </div>
</main>


<% if ("update".equals(modalAtivo) || "create".equals(modalAtivo)) { %>

<div id="modal-overlay">

    <div class="modal">

        <p class="modal-titulo">
            <%= "update".equals(modalAtivo) ? "Editar Nota" : "Lançar Nota" %>
        </p>

        <hr>

        <form action="${pageContext.request.contextPath}/nota-<%= "update".equals(modalAtivo) ? "update" : "create" %>" method="post">

            <input type="hidden" name="idTurma" value="<%= idTurma %>"/>

            <% if ("update".equals(modalAtivo) && notaModal != null) { %>
            <input type="hidden" name="id" value="<%= notaModal.getId() %>"/>
            <% } %>

            <div class="modal-campos">

                <div class="modal-campo">

                    <label>Aluno</label>

                    <div class="input-content">

                        <select name="fkAlunoId" required>

                            <option value="">Selecione...</option>

                            <%
                                if (listaAlunos != null) {
                                    for (Aluno a : listaAlunos) {
                                        boolean selecionado = (notaModal != null && notaModal.getFkAlunoId()==a.getId());
                            %>

                            <option value="<%= a.getId() %>" <%= selecionado ? "selected" : "" %>>
                                <%= a.getUsuario().getNome() %> <%= a.getUsuario().getSobrenome() %>
                            </option>

                            <%
                                    }
                                }
                            %>

                        </select>

                    </div>
                </div>


                <div class="modal-campo">

                    <label>Disciplina</label>

                    <div class="input-content">

                        <select name="fkDisciplinaId" required>

                            <option value="">Selecione...</option>

                            <%
                                if (listaDisciplinas != null) {
                                    for (Disciplina d : listaDisciplinas) {
                                        boolean selecionado = (notaModal != null && notaModal.getFkDisciplinaId()==d.getId());
                            %>

                            <option value="<%= d.getId() %>" <%= selecionado ? "selected" : "" %>>
                                <%= d.getNome() %>
                            </option>

                            <%
                                    }
                                }
                            %>

                        </select>

                    </div>
                </div>


                <div class="modal-campo">
                    <label>Tipo</label>
                    <div class="input-content">
                        <input type="text" name="tipo" value="<%= (notaModal!=null)?notaModal.getTipo():"" %>" required/>
                    </div>
                </div>


                <div class="modal-campo">
                    <label>Semestre</label>
                    <div class="input-content">
                        <input type="number" name="semestre" value="<%= (notaModal!=null)?notaModal.getSemestre():"" %>" required/>
                    </div>
                </div>


                <div class="modal-campo">
                    <label>Ano</label>
                    <div class="input-content">
                        <input type="number" name="ano" value="<%= (notaModal!=null)?notaModal.getAno():"" %>" required/>
                    </div>
                </div>


                <div class="modal-campo">
                    <label>Nota</label>
                    <div class="input-content">
                        <input type="text" name="nota" value="<%= (notaModal!=null)?notaModal.getNota():"" %>" required/>
                    </div>
                </div>

            </div>


            <div class="modal-botoes">

                <a href="${pageContext.request.contextPath}/nota-read?idTurma=<%= idTurma %>"
                   id="btn-cancelar"
                   style="text-decoration:none;color:white;">
                    Cancelar
                </a>

                <button type="submit" id="btn-adicionar">
                    Salvar
                </button>

            </div>

        </form>
    </div>
</div>

<% } %>

</body>
</html>