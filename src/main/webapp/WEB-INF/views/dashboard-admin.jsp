<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>

<%
Usuario usuarioLogado   = (Usuario) session.getAttribute("usuarioLogado");
String contextPath      = request.getContextPath();

Integer totalAlunos      = (Integer) request.getAttribute("totalAlunos");
Integer totalDisciplinas = (Integer) request.getAttribute("totalDisciplinas");
String mediaGeral        = (String) request.getAttribute("mediaGeral");
String melhorAluno       = (String) request.getAttribute("melhorAluno");
String desempenhoJson    = (String) request.getAttribute("desempenhoJson");
String alunosJson        = (String) request.getAttribute("alunosJson");
String disciplinasJson   = (String) request.getAttribute("disciplinasJson");
String erro              = (String) request.getAttribute("erro");

if (totalAlunos == null)      totalAlunos = 0;
if (totalDisciplinas == null) totalDisciplinas = 0;
if (mediaGeral == null)       mediaGeral = "0.00";
if (melhorAluno == null)      melhorAluno = "—";
if (desempenhoJson == null)   desempenhoJson = "[]";
if (alunosJson == null)       alunosJson = "[]";
if (disciplinasJson == null)  disciplinasJson = "[]";
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
    <a href="${pageContext.request.contextPath}/adicionar-view">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-adicionar.png" alt="" />
      Adicionar
    </a>
    <a href="${pageContext.request.contextPath}/professor-read">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-professores.png" alt="" />
      Professores
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
          <%= (usuarioLogado != null && usuarioLogado.getNome() != null)
                ? usuarioLogado.getNome()
                : "Admin" %>
        </strong>
        Super Administrador
      </span>
  </div>
</aside>

<main>
  <header>Dashboard</header>

  <div id="conteudo">
    <% if (erro != null && !erro.isEmpty()) { %>
    <div style="background:#ffe5e5; border:1px solid #f44336; color:#b71c1c;
                    padding:1rem 1.5rem; border-radius:0.5rem; margin-bottom:1.5rem;">
      <%= erro %>
    </div>
    <% } %>

    <section id="dashboard">

      <div class="card verde">
        <div class="info">
          <h2>Total de Alunos</h2>
          <p id="total"><%= totalAlunos %></p>
        </div>
      </div>

      <div class="card roxo">
        <div class="info">
          <h2>Média Geral</h2>
          <p id="mediaG"><%= mediaGeral %></p>
        </div>
      </div>

      <div class="card verde">
        <div class="info">
          <h2>Quantidade de disciplinas</h2>
          <p id="qtd_disciplinas"><%= totalDisciplinas %></p>
        </div>
      </div>

      <div class="card roxo">
        <div class="info">
          <h2>Melhor Aluno</h2>
          <p id="melhor_aluno"><%= melhorAluno %></p>
        </div>
      </div>

      <div class="card grafico">
        <h2>Desempenho Geral</h2>

        <div class="filtros">
          <select id="alunoSelect">
            <option value="">Todos os alunos</option>
          </select>

          <select id="disciplinaSelect">
            <option value="">Todas as disciplinas</option>
          </select>

          <button onclick="atualizarGrafico()">Filtrar</button>
        </div>

        <canvas id="grafico"></canvas>
      </div>

    </section>
  </div>
</main>

<script>
  const desempenho  = <%= desempenhoJson %>;
  const todosAlunos = <%= alunosJson %>;
  const todasDisc   = <%= disciplinasJson %>;

  let graficoInstance = null;

  function preencherSelects() {
    const selAluno = document.getElementById("alunoSelect");
    const selDisc  = document.getElementById("disciplinaSelect");

    todosAlunos.forEach(a => {
      const opt = document.createElement("option");
      opt.value = a.id;
      opt.textContent = a.nome;
      selAluno.appendChild(opt);
    });

    todasDisc.forEach(d => {
      const opt = document.createElement("option");
      opt.value = d.id;
      opt.textContent = d.nome;
      selDisc.appendChild(opt);
    });
  }

  function atualizarGrafico() {
    const idAluno = document.getElementById("alunoSelect").value;
    const idDisc  = document.getElementById("disciplinaSelect").value;

    let filtrado = desempenho;
    if (idAluno) filtrado = filtrado.filter(d => String(d.idAluno) === String(idAluno));
    if (idDisc)  filtrado = filtrado.filter(d => String(d.idDisciplina) === String(idDisc));

    let labels, medias;

    if (idAluno && !idDisc) {
      labels = filtrado.map(d => d.nomeDisciplina);
      medias = filtrado.map(d => d.media);
    } else if (!idAluno && idDisc) {
      labels = filtrado.map(d => d.nomeAluno);
      medias = filtrado.map(d => d.media);
    } else if (idAluno && idDisc) {
      labels = filtrado.map(d => d.nomeAluno + " / " + d.nomeDisciplina);
      medias = filtrado.map(d => d.media);
    } else {
      const agrupado = {};
      filtrado.forEach(d => {
        if (!agrupado[d.idAluno]) {
          agrupado[d.idAluno] = { nome: d.nomeAluno, soma: 0, count: 0 };
        }
        agrupado[d.idAluno].soma  += d.media;
        agrupado[d.idAluno].count += 1;
      });
      labels = Object.values(agrupado).map(a => a.nome);
      medias = Object.values(agrupado).map(a =>
              a.count > 0 ? parseFloat((a.soma / a.count).toFixed(2)) : 0
      );
    }

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
    preencherSelects();
    atualizarGrafico();
  };
</script>

</body>
</html>
