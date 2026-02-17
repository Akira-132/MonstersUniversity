package com.example.servlet.ServletTurmaAluno;

import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.TurmaAlunoDAO;
import com.example.dao.TurmaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Aluno;
import com.example.models.Turma;
import com.example.models.TurmaAluno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/turmaaluno-create")
public class CreateTurmaAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fkAlunoIdStr = request.getParameter("fkAlunoId");
        String fkTurmaIdStr = request.getParameter("fkTurmaId");

        TurmaAlunoDAO dao = new TurmaAlunoDAO();
        boolean success = false;
        String erro = null;

        try {
            int fkAlunoId = Integer.parseInt(fkAlunoIdStr);
            int fkTurmaId = Integer.parseInt(fkTurmaIdStr);

            TurmaAluno novaMatricula = new TurmaAluno(fkAlunoId, fkTurmaId);
            success = dao.create(novaMatricula);

            if (!success) erro = "Erro ao matricular aluno na turma.";

        } catch (NumberFormatException e) {
            erro = "Selecione um aluno e uma turma válidos.";
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                erro = "Este aluno já está matriculado nesta turma.";
            } else {
                erro = "Erro: " + e.getMessage();
            }
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/turmaaluno-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("fkAlunoId_previo", fkAlunoIdStr);
        request.setAttribute("fkTurmaId_previo", fkTurmaIdStr);
        request.setAttribute("modalAtivo", "create");

        // Recarregar listas para os selects não quebrarem
        try {
            carregarListasAuxiliares(request);
            request.setAttribute("listaTurmaAlunos", dao.read());
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/pages/turmaaluno.jsp").forward(request, response);
    }

    private void carregarListasAuxiliares(HttpServletRequest request) throws Exception {
        AlunoDAO alunoDAO = new AlunoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Aluno> alunos = alunoDAO.read();
        for (Aluno a : alunos) {
            a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
        }
        request.setAttribute("listaAlunos", alunos);

        TurmaDAO turmaDAO = new TurmaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        List<Turma> turmas = turmaDAO.read();
        for (Turma t : turmas) {
            t.setDisciplina(disciplinaDAO.readById(t.getFkDisciplinaId()));
        }
        request.setAttribute("listaTurmas", turmas);
    }
}