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

@WebServlet("/disciplina-delete")
public class DeleteDisciplina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DisciplinaDAO dao = new DisciplinaDAO();
        int id = 0;
        boolean success = false;
        String erro = null;

        try {
            String idParam = request.getParameter("id");
            id = Integer.parseInt(idParam);

            int resultado = dao.deleteById(id);

            if (resultado > 0) {
                success = true;
            } else {
                erro = "Não foi possível deletar a disciplina.";
            }

        } catch (NumberFormatException e) {
            erro = "ID inválido.";
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("violates foreign key constraint")) {
                erro = "Não é possível excluir: Disciplina vinculada a turmas.";
            } else {
                erro = "Erro de banco: " + e.getMessage();
            }
        } catch (Exception e) {
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/disciplina-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaDisciplinas", dao.read());
            ProfessorDAO professorDAO = new ProfessorDAO();
            request.setAttribute("listaProfessores", professorDAO.read());
        } catch (Exception e) {}

        if (id > 0) {
            try {
                Disciplina d = dao.readById(id);
                if(d != null) request.setAttribute("disciplinaModal", d);
            } catch (Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}