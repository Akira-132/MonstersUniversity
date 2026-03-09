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
        String idTurmaStr = request.getParameter("idTurma");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);

            // busca o usuário completo do banco para preservar email, senha, cpf
            Usuario usuario = usuarioDAO.readById(idUsuario);
            if (usuario == null) throw new SQLException("Usuário não encontrado.");

            // atualiza apenas nome e sobrenome
            usuario.setNome(nome);
            usuario.setSobrenome(sobrenome);

            if (usuarioDAO.update(usuario) > 0) {
                String redirect = request.getContextPath()
                        + "/turma-aluno-read?sucesso=alunoAtualizado";
                if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
                    redirect += "&id=" + idTurmaStr;
                }
                response.sendRedirect(redirect);
                return;
            }

        } catch (IllegalArgumentException e) {
            // erro de parsing
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String urlErro = request.getContextPath()
                + "/turma-aluno-read?erro=update";
        if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
            urlErro += "&id=" + idTurmaStr;
        }
        response.sendRedirect(urlErro);
    }
}