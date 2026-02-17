package com.example.servlet.ServletDisciplina;

import com.example.dao.DisciplinaDAO;
import com.example.dao.ProfessorDAO;
import com.example.models.Disciplina;
import com.example.models.Professor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/disciplina-create")
public class CreateDisciplina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String fkProfessorIdStr = request.getParameter("fkProfessorId");

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        boolean success = false;
        String erro = null;

        try {
            int fkProfessorId = Integer.parseInt(fkProfessorIdStr);
            Disciplina novaDisciplina = new Disciplina(nome, fkProfessorId);

            success = disciplinaDAO.create(novaDisciplina);

            if (!success) {
                erro = "Erro ao cadastrar disciplina.";
            }

        } catch (NumberFormatException | NullPointerException e) {
            erro = "Erro de validação: Professor inválido.";

        } catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("UNIQUE")) {
                erro = "Erro: Disciplina já cadastrada.";
            } else {
                erro = "Erro de banco: " + e.getMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/disciplina-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("fkProfessorId_previo", fkProfessorIdStr);

        try {
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());
            ProfessorDAO professorDAO = new ProfessorDAO();
            request.setAttribute("listaProfessores", professorDAO.read());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("modalAtivo", "create");
        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}