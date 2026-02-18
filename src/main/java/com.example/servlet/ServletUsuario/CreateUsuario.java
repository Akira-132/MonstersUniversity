package com.example.servlet.ServletUsuario;

import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        String tipo = request.getParameter("tipo");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha, tipo);

            if (usuarioDAO.create(novoUsuario)) {
                response.sendRedirect(request.getContextPath() + "/usuario-read");
                return;
            } else {
                erro = "Erro ao cadastrar usuário no banco.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                erro = "Este e-mail já está em uso.";
            } else {
                erro = "Erro de banco de dados.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao cadastrar usuário.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("tipo_previo", tipo);

        try {
            request.setAttribute("listaUsuarios", usuarioDAO.read());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro crítico: Não foi possível carregar a lista de usuários.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}