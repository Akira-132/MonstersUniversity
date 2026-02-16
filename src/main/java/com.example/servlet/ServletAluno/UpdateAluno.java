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
import java.util.ArrayList;

@WebServlet("/aluno-update")
public class UpdateAluno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/aluno-read");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        String idAlunoStr = request.getParameter("id");
        String idUsuarioStr = request.getParameter("idUsuario");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        boolean success = false;
        String erro = null;
        int idAluno = 0;

        try {
            idAluno = Integer.parseInt(idAlunoStr);
            int idUsuario = Integer.parseInt(idUsuarioStr);

            Usuario usuarioParaAtualizar = usuarioDAO.readById(idUsuario);

            if (usuarioParaAtualizar != null) {
                usuarioParaAtualizar.setNome(nome);
                usuarioParaAtualizar.setSobrenome(sobrenome);
                usuarioParaAtualizar.setEmail(email);

                if (senha != null && !senha.trim().isEmpty()) {
                    usuarioParaAtualizar.setSenha(senha);
                }

                int result = usuarioDAO.update(usuarioParaAtualizar);

                if (result > 0) {
                    success = true;
                } else {
                    erro = "Erro ao atualizar dados do usuário.";
                }
            } else {
                erro = "Usuário vinculado não encontrado.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/aluno-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("modalAtivo", "update");

        List<Aluno> listaAlunos = new ArrayList<>();
        try {
            listaAlunos = alunoDAO.read();
        } catch (Exception e) {}
        request.setAttribute("listaAlunos", listaAlunos);

        if (idAluno > 0) {
            try {
                Aluno a = alunoDAO.readById(idAluno);
                if(a != null) {
                    Usuario u = usuarioDAO.readById(a.getFkUsuarioId());
                    a.setUsuario(u);
                    request.setAttribute("alunoModal", a);
                }
            } catch(Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}