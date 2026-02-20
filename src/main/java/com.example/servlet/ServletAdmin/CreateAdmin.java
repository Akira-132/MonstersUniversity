package com.example.servlet.ServletAdmin;

import com.example.dao.AdminDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Admin;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin-create")
public class CreateAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdminDAO adminDAO = new AdminDAO();
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

            Admin novoAdmin = new Admin(usuarioBanco.getId());

            if (!adminDAO.create(novoAdmin)) {
                usuarioDAO.deleteById(usuarioBanco.getId());
                throw new SQLException("Erro ao vincular perfil de administrador.");
            }

            response.sendRedirect(request.getContextPath() + "/admin-read");
            return;

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                erro = "Este e-mail já está em uso.";
            } else {
                erro = "Erro de banco de dados ao salvar administrador.";
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

        try {
            request.setAttribute("listaAdmins", adminDAO.read());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro crítico: Não foi possível carregar a lista de administradores.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}