package com.example.servlet.ServletDisciplina;

import java.io.IOException;
import java.util.List;
import com.example.models.Disciplina;
import com.example.dao.DisciplinaDAO;
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

        try {
            List<Disciplina> lista = disciplinaDAO.read();
            request.setAttribute("listaDisciplinas", lista);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar disciplinas.");
        }

        request.getRequestDispatcher("/WEB-INF/views/disciplinas.jsp").forward(request, response);
    }
}