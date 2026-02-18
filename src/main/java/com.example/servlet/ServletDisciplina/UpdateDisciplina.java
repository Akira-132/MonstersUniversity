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
        ProfessorDAO professorDAO = new ProfessorDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            int fkProfessorId = Integer.parseInt(idProfessorStr);

            Disciplina disciplina = disciplinaDAO.readById(id);
            if (disciplina == null) throw new Exception("Disciplina não encontrada.");

            disciplina.setNome(nome);
            disciplina.setFkProfessorId(fkProfessorId);

            if (disciplinaDAO.update(disciplina) > 0) {
                response.sendRedirect(request.getContextPath() + "/disciplina-read");
                return;
            } else {
                erro = "Erro ao atualizar no banco.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");

        try {
            List<Disciplina> lista = disciplinaDAO.read();
            for (Disciplina d : lista) {
                Professor p = professorDAO.readById(d.getFkProfessorId());
                if(p != null) p.setUsuario(usuarioDAO.readById(p.getFkUsuarioId()));
                d.setProfessor(p);
            }
            request.setAttribute("listaDisciplinas", lista);

            List<Professor> listaProfs = professorDAO.read();
            for (Professor p : listaProfs) p.setUsuario(usuarioDAO.readById(p.getFkUsuarioId()));
            request.setAttribute("listaProfessores", listaProfs);

            if (idStr != null) {
                request.setAttribute("disciplinaModal", disciplinaDAO.readById(Integer.parseInt(idStr)));
            }

        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}