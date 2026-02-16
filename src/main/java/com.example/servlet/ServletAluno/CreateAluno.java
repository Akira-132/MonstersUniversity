package com.example.servlet.ServletAluno;

import com.example.dao.AlunoDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Aluno;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/aluno-create")
public class CreateAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String tipo = "aluno";

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        boolean success = false;
        String erro = null;

        try {
            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha, tipo);
            boolean usuarioCriado = usuarioDAO.create(novoUsuario);

            if (usuarioCriado) {
                Usuario usuarioBanco = usuarioDAO.readByEmail(email);

                if (usuarioBanco != null) {
                    Aluno novoAluno = new Aluno(cpf, usuarioBanco.getId());
                    success = alunoDAO.create(novoAluno);
                } else {
                    erro = "Erro: Usuário criado mas ID não encontrado.";
                }
            } else {
                erro = "Erro ao criar o usuário base.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate entry") || e.getMessage().contains("UNIQUE")) {
                erro = "Erro: E-mail ou CPF já cadastrados.";
            } else {
                erro = "Erro de banco: " + e.getMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/aluno-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("cpf_previo", cpf);

        List<Aluno> listaAlunos = new ArrayList<>();
        try {
            listaAlunos = alunoDAO.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listaAlunos", listaAlunos);

        request.setAttribute("modalAtivo", "create");
        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}