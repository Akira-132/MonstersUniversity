<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Turma" %>
<%@ page import="com.example.models.Disciplina" %>
<%@ page import="com.example.models.Professor" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    List<Turma> listaTurmas = (List<Turma>) request.getAttribute("listaTurmas");
    List<Disciplina> listaDisciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
    List<Professor> listaProfessores = (List<Professor>) request.getAttribute("listaProfessores");
    String erro = (String) request.getAttribute("erro");
    String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";

    String contextPath = request.getContextPath();

    String sucessoParam = request.getParameter("sucesso");
    String erroParam = request.getParameter("erro");

    String mensagemSucesso = null;
    String mensagemErro = null;

    if ("turmaAdicionada".equals(sucessoParam)) {
        mensagemSucesso = "Turma adicionada com sucesso!";
    } else if ("turmaAtualizada".equals(sucessoParam)) {
        mensagemSucesso = "Turma atualizada com sucesso!";
    } else if ("turmaExcluida".equals(sucessoParam)) {
        mensagemSucesso = "Turma excluída com sucesso!";
    } else if ("disciplinaAdicionada".equals(sucessoParam)) {
        mensagemSucesso = "Disciplina adicionada com sucesso!";
    }

    if ("create".equals(erroParam)) {
        mensagemErro = "Erro ao adicionar turma.";
    } else if ("update".equals(erroParam)) {
        mensagemErro = "Erro ao atualizar turma.";
    } else if ("delete".equals(erroParam)) {
        mensagemErro = "Erro ao excluir turma.";
    } else if (erro != null) {
        mensagemErro = erro;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/turmasAdm.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/modal.css">

    <title>Monsters University - Administração</title>

    <style>
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
        .overlay-dinamico { display: none; }
        .turma-card { cursor: default; }
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

<% if (mensagemSucesso != null) { %>
<script>
    window.addEventListener("load", function() {
        const alertBox = document.createElement("div");
        alertBox.innerText = "<%= mensagemSucesso %>";
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
        setTimeout(() => { alertBox.style.opacity = "1"; }, 100);
        setTimeout(() => {
            alertBox.style.opacity = "0";
            setTimeout(() => alertBox.remove(), 400);
        }, 4000);
    });
</script>
<% } %>

<% if (mensagemErro != null) { %>
<script>
    window.addEventListener("load", function() {
        const alertBox = document.createElement("div");
        alertBox.innerText = "<%= mensagemErro %>";
        alertBox.style.position = "fixed";
        alertBox.style.top = "20px";
        alertBox.style.left = "50%";
        alertBox.style.transform = "translateX(-50%)";
        alertBox.style.backgroundColor = "#FDE8E8";
        alertBox.style.color = "#7c1a1a";
        alertBox.style.padding = "15px 25px";
        alertBox.style.borderRadius = "8px";
        alertBox.style.boxShadow = "0 4px 10px rgba(0,0,0,0.15)";
        alertBox.style.fontFamily = "Montserrat";
        alertBox.style.fontSize = "14px";
        alertBox.style.zIndex = "9999";
        alertBox.style.opacity = "0";
        alertBox.style.transition = "opacity 0.4s ease";
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
        <h2>Nova Turma</h2>
        <form action="${pageContext.request.contextPath}/turma-create" method="post">
            <div class="campo">
                <label for="sala-new">Sala</label>
                <input type="text" id="sala-new" name="sala" placeholder="Ex: B1" required />
            </div>
            <div class="campo">
                <label for="disc-new">Disciplina</label>
                <select id="disc-new" name="fkDisciplinaId" required>
                    <option value="">Selecione</option>
                    <% if (listaDisciplinas != null) {
                        for (Disciplina d : listaDisciplinas) { %>
                    <option value="<%= d.getId() %>">
                        <%= d.getNome() != null ? d.getNome() : "Disciplina" %>
                    </option>
                    <% } } %>
                </select>
            </div>
            <div class="campo">
                <label for="periodo-new">Período</label>
                <select id="periodo-new" name="periodo">
                    <option value="manha">Manhã</option>
                    <option value="tarde">Tarde</option>
                    <option value="noite">Noite</option>
                </select>
            </div>
            <div class="modal-botoes">
                <label for="modal-adicionar" class="btn-cancelar">Cancelar</label>
                <button type="submit" class="btn-confirmar">Adicionar</button>
            </div>
        </form>
    </div>
</div>

<input type="checkbox" id="modal-adicionar-disciplina" hidden />

<div id="overlay-adicionar-disciplina">
    <div class="modal">
        <h2>Nova Disciplina</h2>
        <form action="${pageContext.request.contextPath}/disciplina-create" method="post">
            <div class="campo">
                <label for="nome-disc">Nome</label>
                <input type="text" id="nome-disc" name="nome" placeholder="Camuflagem" required />
            </div>
            <div class="campo">
                <label for="professor-disc">Professor</label>
                <select id="professor-disc" name="fkProfessorId" required>
                    <option value="">Selecione</option>
                    <% if (listaProfessores != null) {
                        for (Professor p : listaProfessores) {
                            String nomeProfessor = "Professor";
                            if (p.getUsuario() != null && p.getUsuario().getNome() != null) {
                                nomeProfessor = p.getUsuario().getNome();
                            } %>
                    <option value="<%= p.getId() %>"><%= nomeProfessor %></option>
                    <% } } %>
                </select>
            </div>
            <div class="modal-botoes">
                <label for="modal-adicionar-disciplina" class="btn-cancelar">Cancelar</label>
                <button type="submit" class="btn-confirmar">Adicionar</button>
            </div>
        </form>
    </div>
</div>

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
                <%= (usuarioLogado != null && usuarioLogado.getNome() != null)
                        ? usuarioLogado.getNome() : "Admin" %>
            </strong>
            Super Administrador
        </span>
    </div>
</aside>

<main>
    <header>Turmas</header>

    <div id="conteudo">

        <div id="topo-turmas">
            <div id="filtro-disciplina">
                <form action="${pageContext.request.contextPath}/turma-read"
                      method="get"
                      style="display:flex;gap:10px;align-items:center;">
                    <label for="disciplina-select">Selecione a disciplina</label>
                    <select id="disciplina-select" name="idDisciplina" onchange="this.form.submit()">
                        <option value="">Todas</option>
                        <%
                            String idSelecionado = request.getParameter("idDisciplina");
                            if (listaDisciplinas != null) {
                                for (Disciplina d : listaDisciplinas) {
                                    boolean selecionado = idSelecionado != null &&
                                            idSelecionado.equals(String.valueOf(d.getId()));
                        %>
                        <option value="<%= d.getId() %>" <%= selecionado ? "selected" : "" %>>
                            <%= d.getNome() %>
                        </option>
                        <% } } %>
                    </select>
                </form>
            </div>

            <div>
                <label for="modal-adicionar-disciplina" id="btn-adicionar-turma" style="margin-right:10px;">
                    + Adicionar Disciplina
                </label>
                <label for="modal-adicionar" id="btn-adicionar-disciplina">
                    + Adicionar turma
                </label>
            </div>
        </div>

        <div id="turmas-lista">
            <%
                if (listaTurmas != null && !listaTurmas.isEmpty()) {
                    int contador = 0;
                    for (Turma t : listaTurmas) {
                        String corBarra = (contador % 2 == 0) ? "verde" : "roxo";
                        contador++;
                        String modalEditId = "modal-editar-" + t.getId();
                        String modalDeleteId = "modal-excluir-" + t.getId();
            %>

            <div class="turma-card">
                <a href="${pageContext.request.contextPath}/turma-aluno-read?id=<%= t.getId() %>" class="turma-link">
                    <span><%= t.getSala() != null ? t.getSala() : "Turma" %></span>
                </a>
                <div class="turma-acoes">
                    <label for="<%= modalEditId %>">
                        <img src="<%= contextPath %>/assets/imgs/icone-editar.png" alt="Editar" class="icone-acao" />
                    </label>
                    <label for="<%= modalDeleteId %>">
                        <img src="<%= contextPath %>/assets/imgs/icone-lixeira.png" alt="Excluir" class="icone-acao" />
                    </label>
                </div>
                <div class="barra <%= corBarra %>"></div>
            </div>

            <input type="checkbox" id="<%= modalEditId %>" hidden />
            <div id="overlay-editar-<%= t.getId() %>" class="overlay-dinamico">
                <div class="modal">
                    <h2>Editar Turma</h2>
                    <form action="<%= contextPath %>/turma-update" method="post">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <div class="campo">
                            <label for="sala-edit-<%= t.getId() %>">Sala</label>
                            <input type="text" id="sala-edit-<%= t.getId() %>" name="sala" value="<%= t.getSala() %>" />
                        </div>
                        <div class="campo">
                            <label for="per-edit-<%= t.getId() %>">Período</label>
                            <select id="per-edit-<%= t.getId() %>" name="periodo">
                                <option value="manha" <%= "manha".equals(t.getPeriodo()) ? "selected" : "" %>>Manhã</option>
                                <option value="tarde" <%= "tarde".equals(t.getPeriodo()) ? "selected" : "" %>>Tarde</option>
                                <option value="noite" <%= "noite".equals(t.getPeriodo()) ? "selected" : "" %>>Noite</option>
                            </select>
                        </div>
                        <div class="modal-botoes">
                            <label for="<%= modalEditId %>" class="btn-cancelar">Cancelar</label>
                            <button type="submit" class="btn-confirmar">Confirmar</button>
                        </div>
                    </form>
                </div>
            </div>

            <input type="checkbox" id="<%= modalDeleteId %>" hidden />
            <div id="overlay-excluir-<%= t.getId() %>" class="overlay-dinamico">
                <div class="modal">
                    <h2>Excluir Turma</h2>
                    <form action="<%= contextPath %>/turma-delete" method="post">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <div class="campo">
                            <label>Sala</label>
                            <input type="text" value="<%= t.getSala() %>" disabled style="border:none;" />
                        </div>
                        <div class="campo">
                            <label for="per-excluir-<%= t.getId() %>">Período</label>
                            <select id="per-excluir-<%= t.getId() %>" name="periodo" disabled>
                                <option value="manha" <%= "manha".equals(t.getPeriodo()) ? "selected" : "" %>>Manhã</option>
                                <option value="tarde" <%= "tarde".equals(t.getPeriodo()) ? "selected" : "" %>>Tarde</option>
                                <option value="noite" <%= "noite".equals(t.getPeriodo()) ? "selected" : "" %>>Noite</option>
                            </select>
                        </div>
                        <div class="campo">
                            <label>Tem certeza que deseja excluir esta turma?</label>
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
            <p>Nenhuma turma encontrada no sistema.</p>
            <% } %>
        </div>
    </div>
</main>
</body>
</html>