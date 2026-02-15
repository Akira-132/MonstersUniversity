package com.example.servlet.ServletUsuario;

import com.example.dao.UsuarioDAO;
import com.example.dao.TelefoneDAO;
import com.example.models.Usuario;
import com.example.models.Telefone;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

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
        String telefoneStr = request.getParameter("telefone");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        boolean success = false;
        String erro = null;

        try {
            Usuario novoUsuario = new Usuario(
                    nome,
                    sobrenome,
                    email,
                    senha,
                    tipo
            );

            int idUsuarioGerado = usuarioDAO.create(novoUsuario);

            if (idUsuarioGerado > 0) {
                Telefone novoTelefone = new Telefone(telefoneStr, idUsuarioGerado);
                boolean telSuccess = telefoneDAO.create(novoTelefone);

                if (telSuccess) {
                    success = true;
                } else {
                    erro = "Usuário criado, mas erro ao salvar telefone.";
                }
            } else {
                erro = "Erro ao cadastrar usuário (DAO retornou ID inválido).";
            }

        } catch (IllegalArgumentException | NullPointerException e) {
            erro = "Erro de validação: " + e.getMessage();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate entry") || e.getMessage().contains("UNIQUE constraint failed")) {
                erro = "Erro: Já existe um usuário com este e-mail.";
            } else {
                erro = "Erro de banco de dados ao criar usuário: " + e.getMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao criar usuário: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/usuarios-crud");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("tipo_previo", tipo);
        request.setAttribute("telefone_previo", telefoneStr);

        List<Usuario> listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = usuarioDAO.read();
        } catch (SQLException readEx) {
            readEx.printStackTrace();
            request.setAttribute("erro", erro + " | Falha ao recarregar lista.");
        }
        request.setAttribute("listaUsuarios", listaUsuarios);

        request.setAttribute("abrirModal", "create");
        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}