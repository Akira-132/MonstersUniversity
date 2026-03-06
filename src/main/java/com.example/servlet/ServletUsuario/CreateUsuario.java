package com.example.servlet.ServletUsuario;

import com.example.dao.UsuarioDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.ProfessorDAO;
import com.example.models.Usuario;
import com.example.models.Aluno;
import com.example.models.Professor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/usuario-create")
public class CreateUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String tipoUsuario = request.getParameter("tipoUsuario");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        String erro = null;
        String sucesso = null;

        try {

            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha);
            boolean usuarioCriado = usuarioDAO.create(novoUsuario);

            if (!usuarioCriado) {
                throw new Exception("Erro ao criar usuário.");
            }
            Usuario usuarioCriadoObj = usuarioDAO.readByEmail(email);

            if (usuarioCriadoObj == null) {
                throw new Exception("Usuário criado mas não encontrado.");
            }

            int usuarioId = usuarioCriadoObj.getId();

            if ("aluno".equals(tipoUsuario)) {

                Aluno aluno = new Aluno(cpf, usuarioId);
                alunoDAO.create(aluno);

                sucesso = "Aluno criado com sucesso!";

            }

            else if ("professor".equals(tipoUsuario)) {

                Professor professor = new Professor(usuarioId);
                professorDAO.create(professor);

                sucesso = "Professor criado com sucesso!";

            }

        }
        catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();
        }
        catch (SQLException e) {
            e.printStackTrace();

            if (e.getMessage().contains("Duplicate")) {
                erro = "Erro: email já cadastrado.";
            } else {
                erro = "Erro no banco de dados.";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (erro != null) {
            request.setAttribute("erro", erro);
        }

        if (sucesso != null) {
            request.getSession().setAttribute("sucesso", sucesso);
        }

        request.getRequestDispatcher("/WEB-INF/views/adicionar.jsp")
                .forward(request, response);
    }
}