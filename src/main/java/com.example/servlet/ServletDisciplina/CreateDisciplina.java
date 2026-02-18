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

@WebServlet("/disciplina-create")
public class CreateDisciplina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String idProfessorStr = request.getParameter("fkProfessorId");

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            int fkProfessorId = Integer.parseInt(idProfessorStr);

            Disciplina novaDisciplina = new Disciplina(nome, fkProfessorId);

            if (disciplinaDAO.create(novaDisciplina)) {
                response.sendRedirect(request.getContextPath() + "/disciplina-read");
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
            erro = "Erro inesperado.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");
        request.setAttribute("nome_previo", nome);

        try {
            List<Disciplina> lista = disciplinaDAO.read();
            for (Disciplina d : lista) {
                Professor p = professorDAO.readById(d.getFkProfessorId());
                if(p != null) p.setUsuario(usuarioDAO.readById(p.getFkUsuarioId()));
                d.setProfessor(p);
            }
            request.setAttribute("listaDisciplinas", lista);

            List<Professor> listaProfs = professorDAO.read();
            for (Professor p : listaProfs) {
                p.setUsuario(usuarioDAO.readById(p.getFkUsuarioId()));
            }
            request.setAttribute("listaProfessores", listaProfs);

        } catch (Exception e) { e.printStackTrace(); }

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}