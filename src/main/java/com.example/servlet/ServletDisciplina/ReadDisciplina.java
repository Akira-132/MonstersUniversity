package com.example.servlet.ServletDisciplina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Aluno;
import com.example.models.Disciplina;
import com.example.models.Turma;
import com.example.models.Usuario;

import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.TurmaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/disciplina-read")
public class ReadDisciplina extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        TurmaDAO turmaDAO = new TurmaDAO();

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        try {
            List<Disciplina> todasDisciplinas = disciplinaDAO.read();
            request.setAttribute("listaDisciplinas", todasDisciplinas);

            if (usuarioLogado != null) {
                Aluno aluno = alunoDAO.readByUsuarioId(usuarioLogado.getId());
                if (aluno != null) {
                    List<Turma> turmasDoAluno = turmaDAO.readAllByAlunoId(aluno.getId());
                    List<Disciplina> disciplinasDoAluno = new ArrayList<>();

                    for (Turma t : turmasDoAluno) {
                        if (t.getDisciplina() != null) {
                            disciplinasDoAluno.add(t.getDisciplina());
                        }
                    }

                    request.setAttribute("disciplinasDoAluno", disciplinasDoAluno);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar disciplinas.");
        }

        request.getRequestDispatcher("/WEB-INF/views/disciplinas.jsp").forward(request, response);
    }
}