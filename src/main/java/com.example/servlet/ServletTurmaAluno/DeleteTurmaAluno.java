package com.example.servlet.ServletTurmaAluno;

import com.example.dao.TurmaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/turma-aluno-delete")
public class DeleteTurmaAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idTurmaStr = request.getParameter("idTurma");
        String idAlunoStr = request.getParameter("idAluno");

        TurmaDAO dao = new TurmaDAO();

        try {
            int idTurma = Integer.parseInt(idTurmaStr);
            int idAluno = Integer.parseInt(idAlunoStr);

            dao.removeAlunoFromTurma(idTurma, idAluno);

            response.sendRedirect(request.getContextPath()
                    + "/turma-aluno-read?sucesso=alunoRemovido&id=" + idTurma);

        } catch (Exception e) {
            e.printStackTrace();
            String urlErro = request.getContextPath();
            if (idTurmaStr != null && !idTurmaStr.isEmpty() && !idTurmaStr.equals("0")) {
                urlErro += "/turma-aluno-read?erro=delete&id=" + idTurmaStr;
            } else {
                urlErro += "/turma-read?erro=delete";
            }
            response.sendRedirect(urlErro);
        }
    }
}