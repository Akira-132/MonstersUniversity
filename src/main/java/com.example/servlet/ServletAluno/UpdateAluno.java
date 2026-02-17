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
import java.util.List;

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
            usuario.setNome(nome);
            usuario.setSobrenome(sobrenome);
            usuario.setEmail(email);
            if (senha != null && !senha.trim().isEmpty()) {
                usuario.setSenha(senha);
            }

            Aluno aluno = alunoDAO.readById(idAluno);
            aluno.setCpf(cpf);

            usuarioDAO.update(usuario);
            alunoDAO.update(aluno);

            response.sendRedirect(request.getContextPath() + "/aluno-read");
            return;

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro ao atualizar: " + e.getMessage();
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");

        try {
            List<Aluno> lista = alunoDAO.read();
            for (Aluno a : lista) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
            request.setAttribute("listaAlunos", lista);
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}