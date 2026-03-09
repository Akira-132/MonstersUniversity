package com.example.servlet.ServletTurmaAluno;

import com.example.dao.TurmaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/turma-aluno-create")
public class CreateTurmaAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idTurmaStr = request.getParameter("fkTurmaId");
        String idAlunoStr = request.getParameter("fkAlunoId");

        TurmaDAO dao = new TurmaDAO();

        try {
            int fkTurmaId = Integer.parseInt(idTurmaStr);
            int fkAlunoId = Integer.parseInt(idAlunoStr);

            if (dao.addAlunoInTurma(fkTurmaId, fkAlunoId)) {
                response.sendRedirect(request.getContextPath() + "/turma-aluno-read?id=" + fkTurmaId + "&sucesso=alunoMatriculado");
            } else {
                response.sendRedirect(request.getContextPath() + "/turma-aluno-read?id=" + fkTurmaId + "&erro=falha");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/turma-read");
        }
    }
}