package com.example.servlet.ServletDisciplina;

import com.example.dao.DisciplinaDAO;
import com.example.dao.ProfessorDAO;
import com.example.models.Disciplina;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/disciplina-update")
public class UpdateDisciplina extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/disciplina-read");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String fkProfessorIdStr = request.getParameter("fkProfessorId");

        boolean success = false;
        String erro = null;
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
            int fkProfessorId = Integer.parseInt(fkProfessorIdStr);

            Disciplina disciplinaAtual = disciplinaDAO.readById(id);

            if (disciplinaAtual != null) {
                disciplinaAtual.setNome(nome);
                disciplinaAtual.setFkProfessorId(fkProfessorId);

                int result = disciplinaDAO.update(disciplinaAtual);
                if (result > 0) {
                    success = true;
                } else {
                    erro = "Erro ao atualizar registro.";
                }
            } else {
                erro = "Disciplina não encontrada.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/disciplina-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("fkProfessorId_previo", fkProfessorIdStr);
        request.setAttribute("modalAtivo", "update");

        try {
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());
            ProfessorDAO professorDAO = new ProfessorDAO();
            request.setAttribute("listaProfessores", professorDAO.read());
        } catch (Exception e) {}

        if (id > 0) {
            try {
                Disciplina d = disciplinaDAO.readById(id);
                if(d != null) request.setAttribute("disciplinaModal", d);
            } catch (Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}