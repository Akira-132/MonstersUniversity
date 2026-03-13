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

@WebServlet("/professor-create")
public class CreateProfessor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        String erro = null;
        Usuario usuarioCriado = null;

        try {
            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha);

            if (!usuarioDAO.create(novoUsuario)) {
                throw new SQLException("Falha ao criar o usuário base.");
            }

            usuarioCriado = usuarioDAO.readByEmail(email);
            if (usuarioCriado == null) {
                throw new SQLException("Erro crítico: Usuário criado, mas ID não encontrado.");
            }

            Professor novoProfessor = new Professor(usuarioCriado.getId());

            if (!professorDAO.create(novoProfessor)) {
                throw new SQLException("Erro ao criar perfil de professor.");
            }

            request.setAttribute("sucesso", "Professor " + nome + " cadastrado com sucesso!");
            request.getRequestDispatcher("/WEB-INF/views/adicionar.jsp").forward(request, response);
            return;

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();

            if (usuarioCriado != null) {
                try {
                    usuarioDAO.deleteById(usuarioCriado.getId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                erro = "Este e-mail já está em uso.";
            } else {
                erro = "Erro de banco de dados ao salvar professor.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao processar a solicitação.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);

        request.getRequestDispatcher("/WEB-INF/views/adicionar.jsp").forward(request, response);
    }
}