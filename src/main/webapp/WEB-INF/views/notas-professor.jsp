<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Nota" %>
<%@ page import="com.example.models.Aluno" %>
<%@ page import="com.example.models.Disciplina" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuarioLogado   = (Usuario) session.getAttribute("usuarioLogado");
    List<Nota> listaNotas   = (List<Nota>) request.getAttribute("listaNotas");
    List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
    List<Disciplina> listaDisciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
    String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";

    String erro = (String) request.getAttribute("erro");
    if (erro == null) {
        erro = (String) session.getAttribute("erro");
        if (erro != null) session.removeAttribute("erro");
    }

    String sucesso = (String) request.getAttribute("sucesso");
    if (sucesso == null) {
        sucesso = (String) session.getAttribute("sucesso");
        if (sucesso != null) session.removeAttribute("sucesso");
    }

    String idTurma = request.getParameter("idTurma");
    if (idTurma == null) idTurma = "0";

    String idDisciplina = (request.getAttribute("idDisciplinaAtual") != null)
            ? String.valueOf(request.getAttribute("idDisciplinaAtual"))
            : "0";

    String sucessoParam = request.getParameter("sucesso");
    String erroParam    = request.getParameter("erro");

    String mensagemSucesso = null;
    String mensagemErro    = null;

    if ("notaCriada".equals(sucessoParam)) {
        mensagemSucesso = "Nota lançada com sucesso!";
    } else if ("notaAtualizada".equals(sucessoParam)) {
        mensagemSucesso = "Nota atualizada com sucesso!";
    } else if ("notaExcluida".equals(sucessoParam)) {
        mensagemSucesso = "Nota excluída com sucesso!";
    } else if (sucesso != null) {
        mensagemSucesso = sucesso;
    }

    if ("create".equals(erroParam)) {
        mensagemErro = "Erro ao lançar nota.";
    } else if ("update".equals(erroParam)) {
        mensagemErro = "Erro ao atualizar nota.";
    } else if ("delete".equals(erroParam)) {
        mensagemErro = "Erro ao excluir nota.";
    } else if (erro != null) {
        mensagemErro = erro;
    }
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

    <style>
        #modal-adicionar:checked ~ #overlay-adicionar,
        input[id^="modal-editar-"]:checked + .overlay-dinamico,
        input[id^="modal-excluir-"]:checked + .overlay-dinamico {
            display: flex;
            position: fixed;
            top: 0; left: 0;
            width: 100vw; height: 100vh;
            background-color: rgba(0,0,0,0.5);
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
        #overlay-adicionar, .overlay-dinamico { display: none; }
    </style>
</head>

<body>

<% if (mensagemSucesso != null) { %>
<script>
    window.addEventListener("load", function () {
        const alertBox = document.createElement("div");
        alertBox.innerText = "<%= mensagemSucesso %>";
        alertBox.style.position        = "fixed";
        alertBox.style.top             = "20px";
        alertBox.style.left            = "50%";
        alertBox.style.transform       = "translateX(-50%)";
        alertBox.style.backgroundColor = "#E8F0FE";
        alertBox.style.color           = "#1a3c7c";
        alertBox.style.padding         = "15px 25px";
        alertBox.style.borderRadius    = "8px";
        alertBox.style.boxShadow       = "0 4px 10px rgba(0,0,0,0.15)";
        alertBox.style.fontFamily      = "Montserrat";
        alertBox.style.fontSize        = "14px";
        alertBox.style.zIndex          = "9999";
        alertBox.style.opacity         = "0";
        alertBox.style.transition      = "opacity 0.4s ease";
        document.body.appendChild(alertBox);
        setTimeout(() => { alertBox.style.opacity = "1"; }, 100);
        setTimeout(() => {
            alertBox.style.opacity = "0";
            setTimeout(() => alertBox.remove(), 400);
        }, 4000);
    });
</script>
<% } %>

<%-- Toast de erro --%>
<% if (mensagemErro != null) { %>
<script>
    window.addEventListener("load", function () {
        const alertBox = document.createElement("div");
        alertBox.innerText = "<%= mensagemErro %>";
        alertBox.style.position        = "fixed";
        alertBox.style.top             = "20px";
        alertBox.style.left            = "50%";
        alertBox.style.transform       = "translateX(-50%)";
        alertBox.style.backgroundColor = "#FDE8E8";
        alertBox.style.color           = "#7c1a1a";
        alertBox.style.padding         = "15px 25px";
        alertBox.style.borderRadius    = "8px";
        alertBox.style.boxShadow       = "0 4px 10px rgba(0,0,0,0.15)";
        alertBox.style.fontFamily      = "Montserrat";
        alertBox.style.fontSize        = "14px";
        alertBox.style.zIndex          = "9999";
        alertBox.style.opacity         = "0";
        alertBox.style.transition      = "opacity 0.4s ease";
        document.body.appendChild(alertBox);
        setTimeout(() => { alertBox.style.opacity = "1"; }, 100);
        setTimeout(() => {
            alertBox.style.opacity = "0";
            setTimeout(() => alertBox.remove(), 400);
        }, 4000);
    });
</script>
<% } %>

<input type="checkbox" id="modal-adicionar" hidden />

<div id="overlay-adicionar">
    <div class="modal">
        <p class="modal-titulo">Lançar Nota</p>
        <hr>
        <form action="${pageContext.request.contextPath}/nota-create" method="post">
            <input type="hidden" name="idTurma" value="<%= idTurma %>"/>
            <div class="modal-campos">
                <div class="modal-campo">
                    <label>Aluno</label>
                    <div class="input-content">
                        <select name="fkAlunoId" required>
                            <option value="">Selecione...</option>
                            <% if (listaAlunos != null) { for (Aluno a : listaAlunos) { %>
                            <option value="<%= a.getId() %>"><%= a.getUsuario().getNome() %> <%= a.getUsuario().getSobrenome() %></option>
                            <% } } %>
                        </select>
                    </div>
                </div>
                <div class="modal-campo">
                    <label>Disciplina</label>
                    <div class="input-content">
                        <select name="fkDisciplinaId" required>
                            <option value="">Selecione...</option>
                            <% if (listaDisciplinas != null) { for (Disciplina d : listaDisciplinas) { %>
                            <option value="<%= d.getId() %>"><%= d.getNome() %></option>
                            <% } } %>
                        </select>
                    </div>
                </div>
                <div class="modal-campo">
                    <label>Tipo</label>
                    <div class="input-content">
                        <select name="tipo" required>
                            <option value="">Selecione...</option>
                            <option value="N1">N1</option>
                            <option value="N2">N2</option>
                        </select>
                    </div>
                </div>
                <div class="modal-campo">
                    <label>Semestre</label>
                    <div class="input-content">
                        <select name="semestre" required>
                            <option value="">Selecione...</option>
                            <option value="1">1º Semestre</option>
                            <option value="2">2º Semestre</option>
                        </select>
                    </div>
                </div>
                <div class="modal-campo">
                    <label>Ano</label>
                    <div class="input-content"><input type="number" name="ano" required/></div>
                </div>
                <div class="modal-campo">
                    <label>Nota</label>
                    <div class="input-content"><input type="text" name="nota" required/></div>
                </div>
            </div>
            <div class="modal-botoes">
                <button type="button" class="btn-cancelar"
                        onclick="document.getElementById('modal-adicionar').checked = false">
                    Cancelar
                </button>
                <button type="submit" class="btn-adicionar">Salvar</button>
            </div>
        </form>
    </div>
</div>

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
            <strong>
                <%= (usuarioLogado != null)
                        ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome()
                        : "Professor" %>
            </strong>
            Professor
        </span>
    </div>
</aside>

<main>

    <header>Minhas Notas</header>

    <div id="conteudo">

        <a href="${pageContext.request.contextPath}/turma-aluno-read?id=<%= idTurma %>"
           id="btn-voltar" style="position: absolute">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-voltar.png" width="36"/>
        </a>

        <div style="display:flex;justify-content:flex-end;align-items:center;margin-bottom:20px;">
            <label for="modal-adicionar" class="btn-adicionar">+ Lançar Nota</label>
        </div>

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
                            String modalEditId   = "modal-editar-"  + n.getId();
                            String modalDeleteId = "modal-excluir-" + n.getId();
                %>

                <tr>
                    <td>
                        <%= n.getAluno().getUsuario().getNome() %>
                        <%= n.getAluno().getUsuario().getSobrenome() %>
                    </td>
                    <td><%= n.getDisciplina().getNome() %></td>
                    <td><%= n.getTipo() %></td>
                    <td><%= n.getSemestre() %>º Sem / <%= n.getAno() %></td>
                    <td>
                        <span class="nota <%= (n.getNota() < 6.0) ? "baixa" : "" %>">
                            <%= String.format("%.1f", n.getNota()) %>
                        </span>
                    </td>
                    <td style="display:flex;gap:10px;justify-content:center;">
                        <label for="<%= modalEditId %>">
                            <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png"
                                 class="icone-editar" style="cursor:pointer;"/>
                        </label>
                        <label for="<%= modalDeleteId %>">
                            <img src="${pageContext.request.contextPath}/assets/imgs/icone-lixeira.png"
                                 class="icone-lixeira" style="cursor:pointer;"/>
                        </label>
                    </td>
                </tr>

                <!-- Modal Editar -->
                <input type="checkbox" id="<%= modalEditId %>" hidden />
                <div class="overlay-dinamico">
                    <div class="modal">
                        <p class="modal-titulo">Editar Nota</p>
                        <hr>
                        <form action="${pageContext.request.contextPath}/nota-update" method="post">
                            <input type="hidden" name="id"            value="<%= n.getId() %>"/>
                            <input type="hidden" name="idTurma"       value="<%= idTurma %>"/>
                            <input type="hidden" name="fkAlunoId"     value="<%= n.getFkAlunoId() %>"/>
                            <input type="hidden" name="fkDisciplinaId" value="<%= n.getFkDisciplinaId() %>"/>
                            <div class="modal-campos">
                                <div class="modal-campo">
                                    <label>Aluno</label>
                                    <div class="input-content">
                                        <input type="text"
                                               value="<%= n.getAluno().getUsuario().getNome() + " " + n.getAluno().getUsuario().getSobrenome() %>"
                                               disabled style="background-color:#999CA1FF;"/>
                                    </div>
                                </div>
                                <div class="modal-campo">
                                    <label>Disciplina</label>
                                    <div class="input-content">
                                        <input type="text" value="<%= n.getDisciplina().getNome() %>"
                                               disabled style="background-color:#999CA1FF;"/>
                                    </div>
                                </div>
                                <div class="modal-campo">
                                    <label>Tipo</label>
                                    <div class="input-content">
                                        <select name="tipo" required>
                                            <option value="">Selecione...</option>
                                            <option value="N1" <%= "N1".equals(n.getTipo()) ? "selected" : "" %>>N1</option>
                                            <option value="N2" <%= "N2".equals(n.getTipo()) ? "selected" : "" %>>N2</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="modal-campo">
                                    <label>Semestre</label>
                                    <div class="input-content">
                                        <select name="semestre" required>
                                            <option value="">Selecione...</option>
                                            <option value="1" <%= n.getSemestre()==1 ? "selected" : "" %>>1º Semestre</option>
                                            <option value="2" <%= n.getSemestre()==2 ? "selected" : "" %>>2º Semestre</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="modal-campo">
                                    <label>Ano</label>
                                    <div class="input-content">
                                        <input type="number" name="ano" value="<%= n.getAno() %>" required/>
                                    </div>
                                </div>
                                <div class="modal-campo">
                                    <label>Nota</label>
                                    <div class="input-content">
                                        <input type="text" name="nota" value="<%= n.getNota() %>" required/>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-botoes">
                                <button type="button" class="btn-cancelar"
                                        onclick="document.getElementById('<%= modalEditId %>').checked = false">
                                    Cancelar
                                </button>
                                <button type="submit" class="btn-adicionar">Salvar</button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Modal Excluir -->
                <input type="checkbox" id="<%= modalDeleteId %>" hidden />
                <div class="overlay-dinamico">
                    <div class="modal">
                        <p class="modal-titulo">Excluir Nota</p>
                        <hr>
                        <form action="${pageContext.request.contextPath}/nota-delete" method="post">
                            <input type="hidden" name="id"          value="<%= n.getId() %>"/>
                            <input type="hidden" name="idTurma"     value="<%= idTurma %>"/>
                            <input type="hidden" name="idDisciplina" value="<%= idDisciplina %>"/>
                            <div class="modal-campos">
                                <div class="modal-campo">
                                    <label>Aluno</label>
                                    <div class="input-content">
                                        <input type="text"
                                               value="<%= n.getAluno().getUsuario().getNome() + " " + n.getAluno().getUsuario().getSobrenome() %>"
                                               disabled style="background-color:#f0f4f8;border:none;"/>
                                    </div>
                                </div>
                                <div class="modal-campo">
                                    <label>Nota</label>
                                    <div class="input-content">
                                        <input type="text"
                                               value="<%= String.format("%.1f", n.getNota()) %>"
                                               disabled style="background-color:#f0f4f8;border:none;"/>
                                    </div>
                                </div>
                                <div class="modal-campo">
                                    <label>Tem certeza que deseja excluir esta nota?</label>
                                </div>
                            </div>
                            <div class="modal-botoes">
                                <button type="button" class="btn-cancelar"
                                        onclick="document.getElementById('<%= modalDeleteId %>').checked = false">
                                    Cancelar
                                </button>
                                <button type="submit" class="btn-adicionar">Confirmar</button>
                            </div>
                        </form>
                    </div>
                </div>

                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6" style="text-align:center;padding:20px;">
                        Nenhuma nota lançada em suas turmas.
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</main>

</body>
</html>
