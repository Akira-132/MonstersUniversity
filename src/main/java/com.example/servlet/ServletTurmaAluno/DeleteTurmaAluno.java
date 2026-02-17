package com.example.servlet.ServletTurmaAluno;

import com.example.dao.TurmaAlunoDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.UsuarioDAO;
import com.example.dao.TurmaDAO;
import com.example.dao.DisciplinaDAO;
import com.example.models.TurmaAluno;
import com.example.models.Aluno;
import com.example.models.Turma;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/turmaaluno-delete")
public class DeleteTurmaAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TurmaAlunoDAO dao = new TurmaAlunoDAO();
        String idStr = request.getParameter("id");
        boolean success = false;
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            if (dao.deleteById(id) > 0) {
                success = true;
            } else {
                erro = "Não foi possível remover a matrícula.";
            }
        } catch (Exception e) {
            erro = e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/turmaaluno-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                TurmaAluno ta = dao.readById(id);
                if (ta != null) {
                    AlunoDAO aDao = new AlunoDAO();
                    UsuarioDAO uDao = new UsuarioDAO();
                    Aluno a = aDao.readById(ta.getFkAlunoId());
                    if(a != null) a.setUsuario(uDao.readById(a.getFkUsuarioId()));
                    ta.setAluno(a);

                    TurmaDAO tDao = new TurmaDAO();
                    DisciplinaDAO dDao = new DisciplinaDAO();
                    Turma t = tDao.readById(ta.getFkTurmaId());
                    if(t != null) t.setDisciplina(dDao.readById(t.getFkDisciplinaId()));
                    ta.setTurma(t);

                    request.setAttribute("turmaAlunoModal", ta);
                }
            } catch(Exception e) {}
        }

        request.getRequestDispatcher("/turmaaluno-read").forward(request, response);
    }
}