package com.example.servlet.ServletProfessor;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.example.models.Professor;
import com.example.models.Usuario;
import com.example.dao.ProfessorDAO;
import com.example.dao.UsuarioDAO;

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
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Professor> lista = professorDAO.read();
            request.setAttribute("listaProfessores", lista);

            if ("prepararUpdate".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Professor p = professorDAO.readById(id);
                if (p != null) {
                    Usuario u = usuarioDAO.readById(p.getFkUsuarioId());
                    p.setUsuario(u);

                    request.setAttribute("professorModal", p);
                    request.setAttribute("modalAtivo", "update");
                }
            }
            else if ("prepararDelete".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Professor p = professorDAO.readById(id);
                if(p != null) {
                    Usuario u = usuarioDAO.readById(p.getFkUsuarioId());
                    p.setUsuario(u);
                    request.setAttribute("professorModal", p);
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

        request.getRequestDispatcher("/WEB-INF/pages/professores.jsp").forward(request, response);
    }
}