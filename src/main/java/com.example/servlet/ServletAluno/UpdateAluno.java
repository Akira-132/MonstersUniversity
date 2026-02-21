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

@WebServlet("/aluno-update")
public class UpdateAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idAlunoStr = request.getParameter("id");
        String idUsuarioStr = request.getParameter("idUsuario");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        String erro = null;

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            int idAluno = Integer.parseInt(idAlunoStr);

            Usuario usuario = usuarioDAO.readById(idUsuario);
            if (usuario == null) throw new SQLException("Usuário não encontrado.");

            usuario.setNome(nome);
            usuario.setSobrenome(sobrenome);
            usuario.setEmail(email);

            if (senha != null && !senha.trim().isEmpty()) {
                usuario.setSenha(senha);
            }

            Aluno aluno = alunoDAO.readById(idAluno);
            if (aluno == null) throw new SQLException("Aluno não encontrado.");

            aluno.setCpf(cpf);

            if (usuarioDAO.update(usuario) > 0 && alunoDAO.update(aluno) > 0) {
                response.sendRedirect(request.getContextPath() + "/aluno-read");
                return;
            } else {
                erro = "Erro ao atualizar dados no banco.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                if (e.getMessage().contains("cpf")) {
                    erro = "Este CPF já pertence a outro aluno.";
                } else {
                    erro = "Este e-mail já está em uso.";
                }
            } else {
                erro = "Erro de banco de dados ao atualizar.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao atualizar.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("cpf_previo", cpf);

        try {
            request.setAttribute("listaAlunos", alunoDAO.read());

            if (idAlunoStr != null) {
                int id = Integer.parseInt(idAlunoStr);
                request.setAttribute("alunoModal", alunoDAO.readById(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista de alunos.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}