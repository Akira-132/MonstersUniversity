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
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (alunoDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/aluno-read");
                return;
            } else {
                erro = "Não foi possível remover o aluno.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Este aluno possui matrículas ou registros vinculados.";
            } else {
                erro = "Erro inesperado ao excluir aluno.";
            }
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaAlunos", alunoDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("alunoModal", alunoDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}