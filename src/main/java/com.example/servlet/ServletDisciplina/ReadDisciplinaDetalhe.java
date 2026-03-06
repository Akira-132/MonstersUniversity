package com.example.servlet.ServletDisciplina;

import com.example.dao.DisciplinaDAO;
import com.example.models.Disciplina;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/disciplina-detalhe-read")
public class ReadDisciplinaDetalhe extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login");
            return;
        }

        String idStr = request.getParameter("id");
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                int idDisciplina = Integer.parseInt(idStr);

                Disciplina disciplina = disciplinaDAO.readById(idDisciplina);

                if (disciplina != null) {
                    request.setAttribute("disciplinaAtual", disciplina);
                } else {
                    request.setAttribute("erro", "Disciplina não encontrada no sistema.");
                }
            } else {
                request.setAttribute("erro", "ID da disciplina não fornecido.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("erro", "ID de disciplina inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar a disciplina.");
        }

        request.getRequestDispatcher("/WEB-INF/views/disciplina-detalhe.jsp").forward(request, response);
    }
}