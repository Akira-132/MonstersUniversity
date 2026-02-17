package com.example.servlet.ServletTurmaAluno;

import com.example.dao.*;
import com.example.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/turmaaluno-read")
public class ReadTurmaAluno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TurmaAlunoDAO dao = new TurmaAlunoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<TurmaAluno> lista = dao.read();

            for (TurmaAluno ta : lista) {
                Aluno a = alunoDAO.readById(ta.getFkAlunoId());
                if(a != null) {
                    a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                    ta.setAluno(a);
                }
                Turma t = turmaDAO.readById(ta.getFkTurmaId());
                if(t != null) {
                    t.setDisciplina(disciplinaDAO.readById(t.getFkDisciplinaId()));
                    ta.setTurma(t);
                }
            }
            request.setAttribute("listaTurmaAlunos", lista);

            List<Aluno> listaAlunos = alunoDAO.read();
            for(Aluno a : listaAlunos) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
            request.setAttribute("listaAlunos", listaAlunos);

            List<Turma> listaTurmas = turmaDAO.read();
            for(Turma t : listaTurmas) t.setDisciplina(disciplinaDAO.readById(t.getFkDisciplinaId()));
            request.setAttribute("listaTurmas", listaTurmas);

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (("prepararUpdate".equals(acao) || "prepararDelete".equals(acao)) && idStr != null) {
                int id = Integer.parseInt(idStr);
                TurmaAluno ta = dao.readById(id);

                if (ta != null) {
                    Aluno a = alunoDAO.readById(ta.getFkAlunoId());
                    if(a != null) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                    ta.setAluno(a);

                    Turma t = turmaDAO.readById(ta.getFkTurmaId());
                    if(t != null) t.setDisciplina(disciplinaDAO.readById(t.getFkDisciplinaId()));
                    ta.setTurma(t);

                    request.setAttribute("turmaAlunoModal", ta);
                    request.setAttribute("modalAtivo", "prepararUpdate".equals(acao) ? "update" : "delete");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/turmaaluno.jsp").forward(request, response);
    }
}