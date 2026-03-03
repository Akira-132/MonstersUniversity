package com.example.servlet.ServletAuth;

import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/aluno-matricula")
public class MatricularAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            String idUsuarioStr = request.getParameter("idUsuario");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            if (idUsuarioStr == null || email == null || senha == null) {
                request.setAttribute("erro", "Dados inválidos.");
                request.getRequestDispatcher("/matricula.jsp")
                        .forward(request, response);
                return;
            }

            int idUsuario = Integer.parseInt(idUsuarioStr);

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Usuario usuarioExistente = usuarioDAO.readById(idUsuario);

            if (usuarioExistente == null) {
                request.setAttribute("erro", "Usuário não encontrado.");
                request.getRequestDispatcher("/matricula.jsp")
                        .forward(request, response);
                return;
            }

            usuarioExistente.setEmail(email);
            usuarioExistente.setSenha(senha);

            usuarioDAO.update(usuarioExistente);

            response.sendRedirect(request.getContextPath() + "/?origem=aluno-sucesso");

        } catch (NumberFormatException e) {

            request.setAttribute("erro", "ID inválido.");
            request.getRequestDispatcher("/matricula.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();

            request.setAttribute("erro", "Erro ao atualizar dados.");
            request.getRequestDispatcher("/matricula.jsp")
                    .forward(request, response);
        }
    }
}