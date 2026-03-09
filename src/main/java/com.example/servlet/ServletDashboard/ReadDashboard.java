package com.example.servlet.ServletDashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.models.Aluno;
import com.example.models.Disciplina;
import com.example.models.Nota;
import com.example.models.Professor;
import com.example.models.Turma;
import com.example.models.Usuario;

import com.example.dao.AdminDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.NotaDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.TurmaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class ReadDashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        AdminDAO adminDAO = new AdminDAO();
        ProfessorDAO profDAO = new ProfessorDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        DisciplinaDAO disciplinaDao = new DisciplinaDAO();
        NotaDAO notaDAO = new NotaDAO();
        TurmaDAO turmaDAO = new TurmaDAO();

        try {
            boolean isAdmin = adminDAO.readByUsuarioId(usuarioLogado.getId()) != null;
            Professor prof = isAdmin ? null : profDAO.readByUsuarioId(usuarioLogado.getId());

            if (!isAdmin && prof == null) {
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            }

            if (isAdmin) {
                carregarAdmin(request, alunoDAO, disciplinaDao, notaDAO);
                request.getRequestDispatcher("/WEB-INF/views/dashboard-admin.jsp").forward(request, response);
            } else {
                carregarProfessor(request, prof, disciplinaDao, turmaDAO, notaDAO);
                request.getRequestDispatcher("/WEB-INF/views/dashboard-professor.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar o dashboard.");
        }
    }

    private void carregarAdmin(HttpServletRequest request, AlunoDAO alunoDAO, DisciplinaDAO disciplinaDao, NotaDAO notaDAO)
            throws Exception {
        List<Aluno> listaAlunos = alunoDAO.read();
        List<Disciplina> listaDisciplina = disciplinaDao.read();
        List<Nota> todasNotas = notaDAO.read();

        Map<Integer, String> nomesPorAluno = new HashMap<>();
        for (Aluno a : listaAlunos) {
            String nome = (a.getUsuario() != null)
                    ? a.getUsuario().getNome() + " " + a.getUsuario().getSobrenome()
                    : "Aluno " + a.getId();
            nomesPorAluno.put(a.getId(), nome.trim());
        }

        Map<Integer, String> nomesPorDisc = new HashMap<>();
        for (Disciplina d : listaDisciplina) {
            nomesPorDisc.put(d.getId(), d.getNome());
        }

        Map<Integer, double[]> somasPorAluno = new HashMap<>();
        Map<String, double[]> somasPorAlunoDisc = new HashMap<>();

        for (Nota n : todasNotas) {
            int idA = n.getFkAlunoId();
            int idD = n.getFkDisciplinaId();

            somasPorAluno.computeIfAbsent(idA, k -> new double[]{0, 0});
            somasPorAluno.get(idA)[0] += n.getNota();
            somasPorAluno.get(idA)[1]++;

            String chave = idA + "_" + idD;
            somasPorAlunoDisc.computeIfAbsent(chave, k -> new double[]{0, 0});
            somasPorAlunoDisc.get(chave)[0] += n.getNota();
            somasPorAlunoDisc.get(chave)[1]++;
        }

        request.setAttribute("totalAlunos", listaAlunos.size());
        request.setAttribute("totalDisciplinas", listaDisciplina.size());
        request.setAttribute("mediaGeral", calcularMediaGeral(somasPorAluno));
        request.setAttribute("melhorAluno", calcularMelhorAluno(somasPorAluno, nomesPorAluno));
        request.setAttribute("desempenhoJson", montarDesempenhoJson(somasPorAlunoDisc, nomesPorAluno, nomesPorDisc));
        request.setAttribute("alunosJson", montarAlunosJson(listaAlunos, nomesPorAluno));
        request.setAttribute("disciplinasJson", montarDiscJson(listaDisciplina));
    }

    private void carregarProfessor(HttpServletRequest request, Professor prof, DisciplinaDAO disciplinaDao,
                                   TurmaDAO turmaDAO, NotaDAO notaDAO)
            throws Exception {
        Disciplina minhaDisciplina = null;
        for (Disciplina d : disciplinaDao.read()) {
            if (d.getFkProfessorId() == prof.getId()) {
                minhaDisciplina = d;
                break;
            }
        }

        if (minhaDisciplina == null) {
            request.setAttribute("totalAlunos", 0);
            request.setAttribute("totalDisciplinas", 0);
            request.setAttribute("nomeDisciplina", "—");
            request.setAttribute("mediaGeral", "0.00");
            request.setAttribute("melhorAluno", "—");
            request.setAttribute("desempenhoJson", "[]");
            request.setAttribute("alunosJson", "[]");
            request.setAttribute("disciplinasJson", "[]");
            return;
        }

        List<Aluno> listaAlunos = new ArrayList<>();
        Map<Integer, String> nomesPorAluno = new HashMap<>();

        for (Turma t : turmaDAO.readByDisciplinaId(minhaDisciplina.getId())) {
            for (Aluno a : t.getAlunos()) {
                if (!nomesPorAluno.containsKey(a.getId())) {
                    String nome = (a.getUsuario() != null)
                            ? a.getUsuario().getNome() + " " + a.getUsuario().getSobrenome()
                            : "Aluno " + a.getId();
                    nomesPorAluno.put(a.getId(), nome.trim());
                    listaAlunos.add(a);
                }
            }
        }

        Map<Integer, String> nomesPorDisc = new HashMap<>();
        nomesPorDisc.put(minhaDisciplina.getId(), minhaDisciplina.getNome());

        Map<Integer, double[]> somasPorAluno     = new HashMap<>();
        Map<String,  double[]> somasPorAlunoDisc = new HashMap<>();

        for (Nota n : notaDAO.readByDisciplinaId(minhaDisciplina.getId())) {
            int idA = n.getFkAlunoId();
            int idD = n.getFkDisciplinaId();

            somasPorAluno.computeIfAbsent(idA, k -> new double[]{0, 0});
            somasPorAluno.get(idA)[0] += n.getNota();
            somasPorAluno.get(idA)[1]++;

            String chave = idA + "_" + idD;
            somasPorAlunoDisc.computeIfAbsent(chave, k -> new double[]{0, 0});
            somasPorAlunoDisc.get(chave)[0] += n.getNota();
            somasPorAlunoDisc.get(chave)[1]++;
        }

        request.setAttribute("totalAlunos",      listaAlunos.size());
        request.setAttribute("totalDisciplinas", 1);
        request.setAttribute("nomeDisciplina",   minhaDisciplina.getNome());
        request.setAttribute("mediaGeral",       calcularMediaGeral(somasPorAluno));
        request.setAttribute("melhorAluno",      calcularMelhorAluno(somasPorAluno, nomesPorAluno));
        request.setAttribute("desempenhoJson",   montarDesempenhoJson(somasPorAlunoDisc, nomesPorAluno, nomesPorDisc));
        request.setAttribute("alunosJson",       montarAlunosJson(listaAlunos, nomesPorAluno));
        request.setAttribute("disciplinasJson",  montarDiscJson(minhaDisciplina));
    }

    private String calcularMediaGeral(Map<Integer, double[]> somasPorAluno) {
        double soma  = 0;
        int count = 0;
        for (double[] dados : somasPorAluno.values()) {
            if (dados[1] > 0) {
                soma += dados[0] / dados[1];
                count++;
            }
        }
        double media = (count > 0) ? soma / count : 0;
        return String.format("%.2f", media).replace(",", ".");
    }

    private String calcularMelhorAluno(Map<Integer, double[]> somasPorAluno, Map<Integer, String> nomesPorAluno) {
        String melhor     = "—";
        double maiorMedia = -1;
        for (Map.Entry<Integer, double[]> entry : somasPorAluno.entrySet()) {
            double[] dados = entry.getValue();
            if (dados[1] > 0) {
                double media = dados[0] / dados[1];
                if (media > maiorMedia) {
                    maiorMedia = media;
                    melhor     = nomesPorAluno.getOrDefault(entry.getKey(), "—");
                }
            }
        }
        return melhor;
    }

    private String montarDesempenhoJson(Map<String, double[]> somasPorAlunoDisc, Map<Integer, String> nomesPorAluno,
                                        Map<Integer, String> nomesPorDisc) {
        StringBuilder sb = new StringBuilder("[");
        boolean primeiro = true;
        for (Map.Entry<String, double[]> entry : somasPorAlunoDisc.entrySet()) {
            String[] partes = entry.getKey().split("_");
            int idA = Integer.parseInt(partes[0]);
            int idD = Integer.parseInt(partes[1]);
            double[] dados = entry.getValue();
            double media = dados[1] > 0 ? dados[0] / dados[1] : 0;

            if (!primeiro) sb.append(",");
            sb.append("{")
                    .append("\"idAluno\":").append(idA).append(",")
                    .append("\"nomeAluno\":\"").append(escapar(nomesPorAluno.getOrDefault(idA, ""))).append("\",")
                    .append("\"idDisciplina\":").append(idD).append(",")
                    .append("\"nomeDisciplina\":\"").append(escapar(nomesPorDisc.getOrDefault(idD, ""))).append("\",")
                    .append("\"media\":").append(String.format("%.2f", media).replace(",", "."))
                    .append("}");
            primeiro = false;
        }
        sb.append("]");
        return sb.toString();
    }

    private String montarAlunosJson(List<Aluno> listaAlunos, Map<Integer, String> nomesPorAluno) {
        StringBuilder sb = new StringBuilder("[");
        boolean primeiro = true;
        for (Aluno a : listaAlunos) {
            if (!primeiro) sb.append(",");
            sb.append("{\"id\":").append(a.getId())
                    .append(",\"nome\":\"").append(escapar(nomesPorAluno.getOrDefault(a.getId(), ""))).append("\"}");
            primeiro = false;
        }
        sb.append("]");
        return sb.toString();
    }

    private String montarDiscJson(List<Disciplina> listaDisciplina) {
        StringBuilder sb = new StringBuilder("[");
        boolean primeiro = true;
        for (Disciplina d : listaDisciplina) {
            if (!primeiro) sb.append(",");
            sb.append("{\"id\":").append(d.getId())
                    .append(",\"nome\":\"").append(escapar(d.getNome())).append("\"}");
            primeiro = false;
        }
        sb.append("]");
        return sb.toString();
    }

    private String montarDiscJson(Disciplina d) {
        return "[{\"id\":" + d.getId() + ",\"nome\":\"" + escapar(d.getNome()) + "\"}]";
    }

    private String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}