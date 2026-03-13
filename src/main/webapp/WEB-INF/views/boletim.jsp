<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Boletim" %>
<%@ page import="java.util.List" %>

<%
  Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
  List<Boletim> listaBoletim = (List<Boletim>) request.getAttribute("listaBoletim");
  String erro = (String) request.getAttribute("erro");
  String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";
  String nomeAluno = (usuarioLogado != null) ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome() : "Aluno";
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/boletim.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css">
  <title>Monsters University</title>
  <style>
    #btn-pdf {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      background-color: #0d47a1;
      color: white;
      border: none;
      border-radius: 0.75rem;
      padding: 0.7rem 1.5rem;
      font-size: 0.9rem;
      font-weight: 700;
      cursor: pointer;
      transition: all 0.2s;
      margin-bottom: 1rem;
      align-self: flex-end;
    }

    #btn-pdf:hover {
      background-color: #1565c0;
      transform: translateY(-2px);
    }

    #btn-pdf svg {
      width: 1.1rem;
      height: 1.1rem;
      fill: white;
    }

    #conteudo {
      display: flex;
      flex-direction: column;
    }
  </style>
</head>

<body>
<aside>
  <div id="logo">
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
  </div>
  <nav>
    <a href="${pageContext.request.contextPath}/disciplina-read">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
      Disciplinas
    </a>
    <a href="${pageContext.request.contextPath}/boletim-read" class="ativo">
      <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
      Boletim
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
      <strong><%= nomeAluno %></strong>
      Aluno
    </span>
  </div>
</aside>

<main>
  <header>Meu Boletim Histórico</header>

  <div id="conteudo">

    <% if (erro != null) { %>
    <div style="color: #ff4d4d; margin-bottom: 15px; text-align: center;"><%= erro %></div>
    <% } %>

    <button id="btn-pdf" onclick="gerarPDF()">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8l-6-6zm-1 1.5L18.5 9H13V3.5zM6 20V4h5v7h7v9H6zm2-5h8v1.5H8V15zm0-3h8v1.5H8V12zm0-3h4v1.5H8V9z"/>
      </svg>
      Gerar PDF
    </button>

    <div id="view-notas">
      <table id="tabela-boletim">
        <thead>
        <tr>
          <th>Disciplina</th>
          <th>Período</th>
          <th>N1</th>
          <th>N2</th>
          <th>Média Final</th>
          <th>Situação</th>
        </tr>
        </thead>
        <tbody>

        <%
          if (listaBoletim != null && !listaBoletim.isEmpty()) {
            for (Boletim b : listaBoletim) {
        %>
        <tr>
          <td><%= b.getDisciplina() %></td>
          <td><%= b.getSemestre() %>º Semestre / <%= b.getAno() %></td>
          <td>
            <span class="nota <%= (b.getMediaP1() != null && b.getMediaP1() < 6.0) ? "baixa" : "" %>">
              <%= (b.getMediaP1() != null) ? String.format("%.1f", b.getMediaP1()) : "—" %>
            </span>
          </td>
          <td>
            <span class="nota <%= (b.getMediaP2() != null && b.getMediaP2() < 6.0) ? "baixa" : "" %>">
              <%= (b.getMediaP2() != null) ? String.format("%.1f", b.getMediaP2()) : "—" %>
            </span>
          </td>
          <td>
            <span class="nota <%= (b.getMediaFinal() != null && b.getMediaFinal() < 6.0) ? "baixa" : "" %>">
              <%= (b.getMediaFinal() != null) ? String.format("%.1f", b.getMediaFinal()) : "—" %>
            </span>
          </td>
          <td>
            <span class="nota <%= "Reprovado".equalsIgnoreCase(b.getSituacao()) ? "baixa" : "" %>">
              <%= b.getSituacao() %>
            </span>
          </td>
        </tr>
        <%
          }
        } else {
        %>
        <tr>
          <td colspan="6" style="text-align: center; padding: 20px;">Nenhum registro de notas encontrado.</td>
        </tr>
        <% } %>

        </tbody>
      </table>
    </div>
  </div>
</main>

<!-- jsPDF + AutoTable via CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.8.2/jspdf.plugin.autotable.min.js"></script>

<script>
  function gerarPDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const nomeAluno = "<%= nomeAluno %>";
    const dataGeracao = new Date().toLocaleDateString("pt-BR");

    // Cabeçalho
    doc.setFontSize(16);
    doc.setTextColor(6, 54, 89);
    doc.text("Monsters University", 14, 18);

    doc.setFontSize(12);
    doc.setTextColor(0, 0, 0);
    doc.text("Boletim Histórico", 14, 26);

    doc.setFontSize(10);
    doc.setTextColor(80, 80, 80);
    doc.text("Aluno: " + nomeAluno, 14, 34);
    doc.text("Emitido em: " + dataGeracao, 14, 40);

    // Linha separadora
    doc.setDrawColor(6, 54, 89);
    doc.line(14, 43, 196, 43);

    // Coleta dados da tabela
    const tabela = document.getElementById("tabela-boletim");
    const cabecalho = [];
    const linhas = [];

    tabela.querySelectorAll("thead tr th").forEach(th => cabecalho.push(th.innerText));
    tabela.querySelectorAll("tbody tr").forEach(tr => {
      const linha = [];
      tr.querySelectorAll("td").forEach(td => linha.push(td.innerText.trim()));
      linhas.push(linha);
    });

    // Gera tabela no PDF
    doc.autoTable({
      head: [cabecalho],
      body: linhas,
      startY: 47,
      headStyles: {
        fillColor: [6, 54, 89],
        textColor: 255,
        fontStyle: "bold",
        fontSize: 9
      },
      bodyStyles: {
        fontSize: 9,
        textColor: 30
      },
      alternateRowStyles: {
        fillColor: [240, 244, 248]
      },
      didDrawCell: function (data) {
        // Colorir células de situação "Reprovado" em vermelho
        if (data.section === "body" && data.column.index === 5) {
          const texto = data.cell.text[0];
          if (texto && texto.toLowerCase() === "reprovado") {
            data.cell.styles.textColor = [180, 0, 0];
            data.cell.styles.fontStyle = "bold";
          }
        }
        // Colorir notas baixas (< 6) em vermelho
        if (data.section === "body" && [2, 3, 4].includes(data.column.index)) {
          const val = parseFloat(data.cell.text[0].replace(",", "."));
          if (!isNaN(val) && val < 6) {
            data.cell.styles.textColor = [180, 0, 0];
            data.cell.styles.fontStyle = "bold";
          }
        }
      },
      margin: { left: 14, right: 14 }
    });

    // Rodapé
    const totalPaginas = doc.internal.getNumberOfPages();
    for (let i = 1; i <= totalPaginas; i++) {
      doc.setPage(i);
      doc.setFontSize(8);
      doc.setTextColor(150);
      doc.text(
              "Página " + i + " de " + totalPaginas,
              doc.internal.pageSize.getWidth() / 2,
              doc.internal.pageSize.getHeight() - 10,
              { align: "center" }
      );
    }

    doc.save("boletim_" + nomeAluno.replace(/ /g, "_") + ".pdf");
  }
</script>

<script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
</body>
</html>
