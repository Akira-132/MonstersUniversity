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
        String tipo = "aluno";

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        String erro = null;

        try {
            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha, tipo);

            if (!usuarioDAO.create(novoUsuario)) {
                throw new SQLException("Falha ao criar usuário base.");
            }

            Usuario usuarioBanco = usuarioDAO.readByEmail(email);

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
            if (e.getMessage().contains("Duplicate")) {
                erro = "E-mail ou CPF já cadastrado.";
            } else {
                e.printStackTrace();
                erro = "Erro no banco de dados.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");

        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("cpf_previo", cpf);

        try {
            List<Aluno> lista = alunoDAO.read();
            for (Aluno a : lista) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
            request.setAttribute("listaAlunos", lista);
        } catch (Exception e) { e.printStackTrace(); }

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}