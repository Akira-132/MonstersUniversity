package com.example.servlet.ServletDisciplina;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.example.models.Disciplina;
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

            request.setAttribute("listaProfessores", professorDAO.read());

            if ("prepararUpdate".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Disciplina d = disciplinaDAO.readById(id);
                if (d != null) {
                    request.setAttribute("disciplinaModal", d);
                    request.setAttribute("modalAtivo", "update");
                }
            }
            else if ("prepararDelete".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Disciplina d = disciplinaDAO.readById(id);
                if (d != null) {
                    request.setAttribute("disciplinaModal", d);
                    request.setAttribute("modalAtivo", "delete");
                }
            }
            else if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao processar dados: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/disciplinas.jsp").forward(request, response);
    }
}