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
                request.getRequestDispatcher("/WEB-INF/views/matricula.jsp")
                        .forward(request, response);
                return;
            }

            int idUsuario = Integer.parseInt(idUsuarioStr);

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Usuario usuarioExistente = usuarioDAO.readById(idUsuario);

            if (usuarioExistente == null) {
                request.setAttribute("erro", "Usuário não encontrado.");
                request.getRequestDispatcher("/WEB-INF/views/matricula.jsp")
                        .forward(request, response);
                return;
            }

            Usuario emailExistente = usuarioDAO.readByEmail(email);
            if (emailExistente != null && emailExistente.getId() != idUsuario) {
                request.setAttribute("erro", "Este e-mail já está em uso por outro usuário.");
                request.getRequestDispatcher("/WEB-INF/views/matricula.jsp").forward(request, response);
                return;
            }

            usuarioExistente.setEmail(email);
            usuarioExistente.setSenha(senha);

            int linhasAfetadas = usuarioDAO.update(usuarioExistente);

            if (linhasAfetadas <= 0) {
                request.setAttribute("erro", "Não foi possível salvar os dados. Tente novamente.");
                request.getRequestDispatcher("/WEB-INF/views/matricula.jsp").forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/?origem=aluno-sucesso");

        } catch (NumberFormatException e) {

            request.setAttribute("erro", "ID inválido.");
            request.getRequestDispatcher("/WEB-INF/views/matricula.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();

            request.setAttribute("erro", "Erro ao atualizar dados.");
            request.getRequestDispatcher("/WEB-INF/views/matricula.jsp")
                    .forward(request, response);
        }
    }
}