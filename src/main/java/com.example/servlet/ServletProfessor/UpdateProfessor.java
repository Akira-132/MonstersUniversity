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
import java.util.List;
import java.util.ArrayList;

@WebServlet("/professor-update")
public class UpdateProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(request.getContextPath() + "/professores-crud");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        String idProfessorStr = request.getParameter("id");
        String idUsuarioStr = request.getParameter("idUsuario");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        boolean success = false;
        String erro = null;
        int idProfessor = 0;

        try {
            idProfessor = Integer.parseInt(idProfessorStr);
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
            response.sendRedirect(request.getContextPath() + "/professores-crud");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("email_previo", email);

        List<Professor> listaProfessores = new ArrayList<>();
        try {
            listaProfessores = professorDAO.read();
        } catch (Exception e) {}
        request.setAttribute("listaProfessores", listaProfessores);

        if (idProfessor > 0) {
            try {
                Professor p = professorDAO.readById(idProfessor);
                if(p != null) {
                    Usuario u = usuarioDAO.readById(p.getFkUsuarioId());
                    p.setUsuario(u);
                    request.setAttribute("professorModal", p);
                }
            } catch(Exception e) {}
        }

        request.setAttribute("abrirModal", "update");
        request.getRequestDispatcher("/WEB-INF/pages/professores.jsp").forward(request, response);
    }
}