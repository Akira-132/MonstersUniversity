package com.example.servlet.ServletNota;

import java.io.IOException;
import java.util.List;

import com.example.models.Nota;
import com.example.models.Usuario;
import com.example.models.Turma;

import java.util.ArrayList;
import com.example.models.Aluno;
import com.example.models.Disciplina;

import com.example.dao.TurmaDAO;
import com.example.dao.NotaDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/nota-read")
public class ReadNota extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NotaDAO notaDAO = new NotaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        TurmaDAO turmaDAO = new TurmaDAO();

        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        String idTurmaStr = request.getParameter("idTurma");
        String idDisciplinaStr = request.getParameter("idDisciplina");

        if ("ok".equals(request.getParameter("sucesso"))) {
            request.setAttribute("sucesso", "Operação realizada com sucesso!");
        }

        List<Nota> lista = null;

        try {

            /* =========================
               FILTRO POR DISCIPLINA
               ========================= */
            if (idDisciplinaStr != null) {

                int idDisciplina = Integer.parseInt(idDisciplinaStr);

                lista = notaDAO.readByDisciplinaId(idDisciplina);

                request.setAttribute("idDisciplinaAtual", idDisciplina);

            }

            /* =========================
               FILTRO POR TURMA
               ========================= */
            else if (idTurmaStr != null) {

                int idTurma = Integer.parseInt(idTurmaStr);

                Turma turma = turmaDAO.readById(idTurma);

                if (turma != null) {

                    int idDisciplina = turma.getFkDisciplinaId();

                    lista = notaDAO.readByDisciplinaId(idDisciplina);

                    request.setAttribute("idTurmaAtual", idTurma);
                    request.setAttribute("idDisciplinaAtual", idDisciplina);

                } else {

                    lista = notaDAO.read();
                }

            }

            /* =========================
               SEM FILTRO
               ========================= */
            else {

                lista = notaDAO.read();

            }

            request.setAttribute("listaNotas", lista);

            /* =========================
               PREPARAÇÃO DE MODAIS
               ========================= */

            if ("prepararCreate".equals(acao) || "prepararUpdate".equals(acao)) {

                // Busca o idDisciplina atual para filtrar os alunos
                Integer idDiscAtual = (Integer) request.getAttribute("idDisciplinaAtual");

                List<Aluno> alunosFiltrados = new ArrayList<>();

                if (idDiscAtual != null) {
                    // Pega alunos só das turmas dessa disciplina
                    for (Turma t : turmaDAO.readByDisciplinaId(idDiscAtual)) {
                        for (Aluno a : t.getAlunos()) {
                            boolean jaAdicionado = alunosFiltrados.stream()
                                    .anyMatch(x -> x.getId() == a.getId());
                            if (!jaAdicionado) {
                                alunosFiltrados.add(a);
                            }
                        }
                    }
                } else {
                    alunosFiltrados = alunoDAO.read();
                }

                List<Disciplina> disciplinas = disciplinaDAO.read();

                request.setAttribute("listaAlunos", alunosFiltrados);
                request.setAttribute("listaDisciplinas", disciplinas);
            }

            if ("prepararCreate".equals(acao)) {

                request.setAttribute("modalAtivo", "create");

            }

            if (idStr != null) {

                int id = Integer.parseInt(idStr);

                Nota nota = notaDAO.readById(id);

                if (nota != null) {

                    request.setAttribute("notaModal", nota);

                    if ("prepararUpdate".equals(acao)) {

                        request.setAttribute("modalAtivo", "update");

                    } else if ("prepararDelete".equals(acao)) {

                        request.setAttribute("modalAtivo", "delete");

                    }

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar dados.");

        }

        try {

            ProfessorDAO professorDAO = new ProfessorDAO();

            if (usuarioLogado != null &&
                    professorDAO.readByUsuarioId(usuarioLogado.getId()) != null) {

                request.getRequestDispatcher("/WEB-INF/views/notas-professor.jsp")
                        .forward(request, response);

            } else {

                request.getRequestDispatcher("/WEB-INF/views/notas-adm.jsp")
                        .forward(request, response);

            }

        } catch (Exception e) {

            request.getRequestDispatcher("/WEB-INF/views/notas-adm.jsp")
                    .forward(request, response);

        }

    }
}