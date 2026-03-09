package com.example.servlet.ServletTurma;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.example.models.Turma;
import com.example.models.Disciplina;
import com.example.models.Professor;
import com.example.models.Usuario;

import com.example.dao.TurmaDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.ProfessorDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/turma-read")
public class ReadTurma extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        TurmaDAO turmaDAO = new TurmaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        Professor prof = null;

        try {
            prof = professorDAO.readByUsuarioId(usuarioLogado.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (prof != null) {

            try {

                List<Disciplina> todasDisciplinas = disciplinaDAO.read();
                List<Turma> turmasProfessor = new ArrayList<>();

                if (todasDisciplinas != null) {

                    for (Disciplina d : todasDisciplinas) {

                        if (d.getFkProfessorId() == prof.getId()) {

                            List<Turma> turmas = turmaDAO.readByDisciplinaId(d.getId());

                            if (turmas != null && !turmas.isEmpty()) {
                                turmasProfessor.addAll(turmas);
                            }
                        }
                    }
                }

                if (!turmasProfessor.isEmpty()) {

                    request.setAttribute("listaTurmas", turmasProfessor);

                } else {

                    request.setAttribute("listaTurmas", new ArrayList<>());
                    request.setAttribute("erro", "Você ainda não possui turmas vinculadas no sistema.");

                }

                request.getRequestDispatcher("/WEB-INF/views/turmas-professor.jsp")
                        .forward(request, response);

                return;

            } catch (Exception e) {

                e.printStackTrace();

                request.setAttribute("listaTurmas", new ArrayList<>());
                request.setAttribute("erro", "Erro ao carregar turmas do professor.");

                request.getRequestDispatcher("/WEB-INF/views/turmas-professor.jsp")
                        .forward(request, response);

                return;
            }
        }

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        String idDisciplinaStr = request.getParameter("idDisciplina");

        if ("ok".equals(request.getParameter("sucesso"))) {
            request.setAttribute("sucesso", "Operação realizada com sucesso!");
        }

        try {

            List<Turma> lista;

            if (idDisciplinaStr != null && !idDisciplinaStr.trim().isEmpty()) {

                try {
                    int idDisciplina = Integer.parseInt(idDisciplinaStr);
                    lista = turmaDAO.readByDisciplinaId(idDisciplina);
                } catch (NumberFormatException e) {
                    lista = turmaDAO.read();
                }

            } else {

                lista = turmaDAO.read();

            }

            if (lista == null) {
                lista = new ArrayList<>();
            }

            request.setAttribute("listaTurmas", lista);
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());
            request.setAttribute("listaProfessores", professorDAO.read());

            if ("prepararCreate".equals(acao)) {

                request.setAttribute("modalAtivo", "create");

            } else if (idStr != null && !idStr.trim().isEmpty()) {

                try {

                    int id = Integer.parseInt(idStr);
                    Turma turma = turmaDAO.readById(id);

                    if (turma != null) {

                        request.setAttribute("turmaModal", turma);

                        if ("prepararUpdate".equals(acao))
                            request.setAttribute("modalAtivo", "update");

                        else if ("prepararDelete".equals(acao))
                            request.setAttribute("modalAtivo", "delete");
                    }

                } catch (NumberFormatException ignored) {
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar dados.");
        }

        request.getRequestDispatcher("/WEB-INF/views/turmas-adm.jsp")
                .forward(request, response);
    }
}