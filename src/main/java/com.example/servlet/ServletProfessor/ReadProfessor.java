package com.example.servlet.ServletProfessor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Professor;
import com.example.models.Usuario;
import com.example.dao.ProfessorDAO;
import com.example.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/professores-crud")
public class ReadProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProfessorDAO professorDAO = new ProfessorDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String pk = request.getParameter("pk");

        if (pk != null && !pk.isEmpty()) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                int idProfessor = Integer.parseInt(pk);
                Professor professor = professorDAO.readById(idProfessor);

                if (professor != null) {
                    Usuario usuario = usuarioDAO.readById(professor.getFkUsuarioId());

                    if (usuario != null) {
                        String json = "{"
                                + "\"id\":\"" + professor.getId() + "\","
                                + "\"idUsuario\":\"" + usuario.getId() + "\","
                                + "\"nome\":\"" + escapeJson(usuario.getNome()) + "\","
                                + "\"sobrenome\":\"" + escapeJson(usuario.getSobrenome()) + "\","
                                + "\"email\":\"" + escapeJson(usuario.getEmail()) + "\""
                                + "}";
                        response.getWriter().write(json);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"erro\":\"Usuário vinculado não encontrado.\"}");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"erro\":\"Professor não encontrado.\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"erro\":\"ID inválido.\"}");
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"erro\":\"Erro de banco: " + e.getMessage() + "\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"erro\":\"Erro: " + e.getMessage() + "\"}");
            }

        } else {

            List<Professor> listaProfessores = new ArrayList<>();
            String erro = null;

            try {
                listaProfessores = professorDAO.read();
            } catch (SQLException e) {
                e.printStackTrace();
                erro = "Erro ao buscar dados: " + e.getMessage();
            }

            request.setAttribute("listaProfessores", listaProfessores);
            if (erro != null) request.setAttribute("erro", erro);

            request.getRequestDispatcher("/WEB-INF/pages/professores.jsp").forward(request, response);
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}