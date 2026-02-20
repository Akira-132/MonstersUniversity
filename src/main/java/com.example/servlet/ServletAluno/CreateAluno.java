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

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        String erro = null;

        try {
            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha);

            if (!usuarioDAO.create(novoUsuario)) {
                throw new SQLException("Falha ao criar o usuário base.");
            }

            Usuario usuarioBanco = usuarioDAO.readByEmail(email);
            if (usuarioBanco == null) {
                throw new SQLException("Erro crítico: Usuário criado, mas ID não encontrado.");
            }

            Aluno novoAluno = new Aluno(cpf, usuarioBanco.getId());

            if (!alunoDAO.create(novoAluno)) {
                usuarioDAO.deleteById(usuarioBanco.getId());
                throw new SQLException("Erro ao criar perfil de aluno.");
            }

            response.sendRedirect(request.getContextPath() + "/aluno-read");
            return;

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                if (e.getMessage().contains("cpf")) {
                    erro = "Este CPF já está cadastrado.";
                } else {
                    erro = "Este e-mail já está em uso.";
                }
            } else {
                erro = "Erro de banco de dados ao salvar aluno.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao processar a solicitação.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("cpf_previo", cpf);

        try {
            request.setAttribute("listaAlunos", alunoDAO.read());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro crítico: Não foi possível carregar a lista de alunos.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}