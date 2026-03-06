package com.example.servlet.ServletProfessor;

import java.io.IOException;
import java.util.List;
import com.example.models.Professor;
import com.example.dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/professor-read")
public class ReadProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProfessorDAO professorDAO = new ProfessorDAO();
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Professor> lista = professorDAO.read();
            request.setAttribute("listaProfessores", lista);

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Professor professorSelecionado = professorDAO.readById(id);

                if (professorSelecionado != null) {
                    request.setAttribute("professorModal", professorSelecionado);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar dados dos professores.");
        }

        request.getRequestDispatcher("/WEB-INF/views/professores.jsp").forward(request, response);
    }
}