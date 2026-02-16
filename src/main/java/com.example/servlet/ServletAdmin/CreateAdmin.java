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
import java.util.ArrayList;
import java.util.List;

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
        String tipo = "admin";

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdminDAO adminDAO = new AdminDAO();
        boolean success = false;
        String erro = null;

        try {
            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha, tipo);

            boolean usuarioCriado = usuarioDAO.create(novoUsuario);

            if (usuarioCriado) {
                Usuario usuarioBanco = usuarioDAO.readByEmail(email);

                if (usuarioBanco != null) {
                    Admin novoAdmin = new Admin(usuarioBanco.getId());
                    success = adminDAO.create(novoAdmin);
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
                erro = "Erro: E-mail já cadastrado.";
            } else {
                erro = "Erro de banco: " + e.getMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);

        List<Admin> listaAdmins = new ArrayList<>();
        try {
            listaAdmins = adminDAO.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listaAdmins", listaAdmins);

        request.setAttribute("modalAtivo", "create");
        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}