package com.example.servlet.ServletDisciplina;

import com.example.dao.DisciplinaDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Disciplina;
import com.example.models.Professor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/disciplina-delete")
public class DeleteDisciplina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (disciplinaDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/disciplina-read");
                return;
            } else {
                erro = "Não foi possível excluir a disciplina.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("foreign key")) {
                erro = "Não é possível excluir: Existem turmas ou notas vinculadas a esta disciplina.";
            } else {
                erro = "Erro inesperado ao excluir.";
            }
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Disciplina> lista = disciplinaDAO.read();
            for (Disciplina d : lista) {
                Professor p = professorDAO.readById(d.getFkProfessorId());
                if(p != null) p.setUsuario(usuarioDAO.readById(p.getFkUsuarioId()));
                d.setProfessor(p);
            }
            request.setAttribute("listaDisciplinas", lista);

            if (request.getParameter("id") != null) {
                request.setAttribute("disciplinaModal", disciplinaDAO.readById(Integer.parseInt(request.getParameter("id"))));
            }
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}