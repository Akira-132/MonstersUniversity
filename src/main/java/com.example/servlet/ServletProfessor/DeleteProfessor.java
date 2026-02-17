package com.example.servlet.ServletProfessor;

import com.example.dao.ProfessorDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Professor;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/professor-delete")
public class DeleteProfessor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProfessorDAO dao = new ProfessorDAO();
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
                erro = "Não foi possível deletar o professor.";
            }

        } catch (NumberFormatException e) {
            erro = "ID inválido.";
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("violates foreign key constraint")) {
                erro = "Não é possível excluir: Professor vinculado a turmas ou disciplinas.";
            } else {
                erro = "Erro de banco: " + e.getMessage();
            }
        } catch (Exception e) {
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/professor-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        List<Professor> lista = new ArrayList<>();
        try { lista = dao.read(); } catch (Exception e) {}
        request.setAttribute("listaProfessores", lista);

        if (id > 0) {
            try {
                Professor p = dao.readById(id);
                if (p != null) {
                    UsuarioDAO uDao = new UsuarioDAO();
                    Usuario u = uDao.readById(p.getFkUsuarioId());
                    p.setUsuario(u);
                    request.setAttribute("professorModal", p);
                }
            } catch (Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/professores.jsp").forward(request, response);
    }
}