package com.example.servlet.ServletDisciplina;

import com.example.dao.DisciplinaDAO;
import com.example.models.Disciplina;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/disciplina-create")
public class CreateDisciplina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String idProfessorStr = request.getParameter("fkProfessorId");

        System.out.println("DEBUG NOME: " + nome);
        System.out.println("DEBUG FK PROFESSOR: " + idProfessorStr);

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String erro = null;

        try {
            int fkProfessorId = Integer.parseInt(idProfessorStr);

            Disciplina novaDisciplina = new Disciplina(nome, fkProfessorId);

            if (disciplinaDAO.create(novaDisciplina)) {

                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;

            } else {
                erro = "Erro ao cadastrar a disciplina no banco.";
            }

        } catch (NumberFormatException e) {
            erro = "Selecione um professor válido.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao cadastrar disciplina.";
        }

        // caso dê erro
        request.setAttribute("erro", erro);

        // redirect evita erro 405
        response.sendRedirect(request.getContextPath() + "/turma-read");
    }
}