package com.example.servlet.ServletProfessor;

import com.example.dao.ProfessorDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Professor;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/professor-update")
public class UpdateProfessor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idProfessorStr = request.getParameter("id");
        String idUsuarioStr = request.getParameter("idUsuario");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        String erro = null;

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            Usuario usuario = usuarioDAO.readById(idUsuario);

            if (usuario == null) {
                throw new SQLException("Usuário original não encontrado.");
            }

            usuario.setNome(nome);
            usuario.setSobrenome(sobrenome);
            usuario.setEmail(email);

            if (senha != null && !senha.trim().isEmpty()) {
                usuario.setSenha(senha);
            }

            if (usuarioDAO.update(usuario) > 0) {
                response.sendRedirect(request.getContextPath() + "/professor-read");
                return;
            } else {
                erro = "Não foi possível atualizar os dados.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                erro = "Este e-mail já pertence a outro usuário.";
            } else {
                erro = "Erro de banco de dados ao atualizar.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao atualizar professor.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);

        try {
            request.setAttribute("listaProfessores", professorDAO.read());

            if (idProfessorStr != null) {
                int id = Integer.parseInt(idProfessorStr);
                request.setAttribute("professorModal", professorDAO.readById(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista de professores.");
            }
        }

        request.getRequestDispatcher("/professor-read").forward(request, response);
    }
}