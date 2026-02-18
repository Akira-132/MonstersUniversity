package com.example.servlet.ServletDisciplina;

import java.io.IOException;
import java.util.List;
import com.example.models.Disciplina;
import com.example.models.Professor;
import com.example.dao.DisciplinaDAO;
import com.example.dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/disciplina-read")
public class ReadDisciplina extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Disciplina> lista = disciplinaDAO.read();
            request.setAttribute("listaDisciplinas", lista);

            if ("prepararCreate".equals(acao) || "prepararUpdate".equals(acao)) {
                List<Professor> listaProfs = professorDAO.read();
                request.setAttribute("listaProfessores", listaProfs);
            }

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Disciplina disciplina = disciplinaDAO.readById(id);

                if (disciplina != null) {
                    request.setAttribute("disciplinaModal", disciplina);
                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar dados.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}