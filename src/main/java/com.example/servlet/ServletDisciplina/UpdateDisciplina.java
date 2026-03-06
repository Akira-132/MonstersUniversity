package com.example.servlet.ServletDisciplina;

import com.example.dao.DisciplinaDAO;
import com.example.models.Disciplina;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/disciplina-update")
public class UpdateDisciplina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String idProfessorStr = request.getParameter("fkProfessorId");

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            int fkProfessorId = Integer.parseInt(idProfessorStr);

            Disciplina disciplina = disciplinaDAO.readById(id);
            if (disciplina == null) throw new Exception("Disciplina não encontrada.");

            disciplina.setNome(nome);
            disciplina.setFkProfessorId(fkProfessorId);

            if (disciplinaDAO.update(disciplina) > 0) {
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            } else {
                erro = "Erro ao atualizar no banco.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        request.setAttribute("erro", erro);
        request.getRequestDispatcher("/turma-read").forward(request, response);
    }
}