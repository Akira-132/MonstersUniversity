package com.example.servlet.ServletAluno;

import com.example.dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/aluno-delete")
public class DeleteAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AlunoDAO alunoDAO = new AlunoDAO();
        String idTurmaStr = request.getParameter("idTurma");

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (alunoDAO.deleteById(id) > 0) {
                String redirect = request.getContextPath()
                        + "/turma-aluno-read?sucesso=alunoRemovido";
                if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
                    redirect += "&id=" + idTurmaStr;
                }
                response.sendRedirect(redirect);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String urlErro = request.getContextPath()
                + "/turma-aluno-read?erro=delete";
        if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
            urlErro += "&id=" + idTurmaStr;
        }
        response.sendRedirect(urlErro);
    }
}