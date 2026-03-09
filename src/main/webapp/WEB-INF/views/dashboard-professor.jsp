<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>

<%
    Usuario usuarioLogado    = (Usuario) session.getAttribute("usuarioLogado");
    String contextPath       = request.getContextPath();

    Integer totalAlunos      = (Integer) request.getAttribute("totalAlunos");
    String nomeDisciplina    = (String)  request.getAttribute("nomeDisciplina");
    String mediaGeral        = (String)  request.getAttribute("mediaGeral");
    String melhorAluno       = (String)  request.getAttribute("melhorAluno");
    String desempenhoJson    = (String)  request.getAttribute("desempenhoJson");
    String alunosJson        = (String)  request.getAttribute("alunosJson");

    if (totalAlunos == null)   totalAlunos   = 0;
    if (nomeDisciplina == null) nomeDisciplina = "—";
    if (mediaGeral == null)    mediaGeral    = "0.00";
    if (melhorAluno == null)   melhorAluno   = "—";
    if (desempenhoJson == null) desempenhoJson = "[]";
    if (alunosJson == null)    alunosJson    = "[]";
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/dashboard.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <title>Dashboard - Monsters University</title>
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
        <a href="${pageContext.request.contextPath}/dashboard" class="ativo">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
            Dashboards
        </a>
    </nav>

    <div id="info-usuario"
         onclick="window.location.href='${pageContext.request.contextPath}/perfil-read'"
         style="cursor: pointer;">
        <div id="avatar">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png" alt="" />
        </div>
        <span>
            <strong>
                <%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Professor" %>
            </strong>
            Professor
        </span>
    </div>
</aside>

<main style="padding: 0">

    <header>
        Dashboard
    </header>

    <div id="conteudo">
        <section id="dashboard">

            <div class="card verde">
                <div class="info">
                    <h2>Total de Alunos</h2>
                    <p><%= totalAlunos %></p>
                </div>
            </div>

            <div class="card roxo">
                <div class="info">
                    <h2>Média Geral</h2>
                    <p><%= mediaGeral %></p>
                </div>
            </div>

            <div class="card verde">
                <div class="info">
                    <h2>Disciplina</h2>
                    <p><%= nomeDisciplina %></p>
                </div>
            </div>

            <div class="card roxo">
                <div class="info">
                    <h2>Melhor Aluno</h2>
                    <p><%= melhorAluno %></p>
                </div>
            </div>

            <div class="card grafico">
                <h2>Desempenho da Turma</h2>

                <div class="filtros">
                    <div style="width: min-content; display: flex; gap: 1rem; align-items: center;">
                        <select id="alunoSelect">
                            <option value="">Todos os alunos</option>
                        </select>
                        <button onclick="atualizarGrafico()" style="margin-bottom: 0">Filtrar</button>
                    </div>
                </div>

                <canvas id="grafico"></canvas>
            </div>

        </section>
    </div>
</main>

<script>
    const desempenho  = <%= desempenhoJson %>;
    const todosAlunos = <%= alunosJson %>;

    let graficoInstance = null;

    function preencherSelect() {
        const sel = document.getElementById("alunoSelect");
        todosAlunos.forEach(a => {
            const opt = document.createElement("option");
            opt.value = a.id;
            opt.textContent = a.nome;
            sel.appendChild(opt);
        });
    }

    function atualizarGrafico() {
        const idAluno = document.getElementById("alunoSelect").value;

        let filtrado = idAluno
            ? desempenho.filter(d => String(d.idAluno) === String(idAluno))
            : desempenho;

        const labels = filtrado.map(d => d.nomeAluno);
        const medias = filtrado.map(d => d.media);

        if (graficoInstance) graficoInstance.destroy();

        graficoInstance = new Chart(document.getElementById("grafico"), {
            type: "bar",
            data: {
                labels: labels,
                datasets: [{
                    label: "Média",
                    data: medias,
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: { beginAtZero: true, max: 10 }
                }
            }
        });
    }

    window.onload = function () {
        preencherSelect();
        atualizarGrafico();
    };
</script>

</body>
</html>
