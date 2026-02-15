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
import java.util.List;
import java.util.ArrayList;

@WebServlet("/usuario-update")
public class UpdateUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> listaUsuarios = new ArrayList<>();
        String erro = null;

        String idParam = request.getParameter("id");

        try {
            listaUsuarios = dao.read();

            Usuario usuarioModal = null;
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                usuarioModal = dao.readById(id);

                if (usuarioModal != null) {
                    request.setAttribute("usuarioModal", usuarioModal);
                    request.setAttribute("abrirModal", "update");
                } else {
                    erro = "Usuário ID " + id + " não encontrado.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco de dados: " + e.getMessage();
        } catch (NumberFormatException e) {
            erro = "ID inválido: " + idParam;
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (erro != null) {
            request.setAttribute("erro", erro);
        }
        request.setAttribute("listaUsuarios", listaUsuarios);

        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        UsuarioDAO dao = new UsuarioDAO();
        int id = 0;
        boolean success = false;
        String erro = null;

        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String tipo = request.getParameter("tipo");

        try {
            id = Integer.parseInt(idParam);

            Usuario usuarioParaAtualizar = dao.readById(id);
            if (usuarioParaAtualizar == null) {
                throw new Exception("Usuário ID " + id + " não encontrado.");
            }

            usuarioParaAtualizar.setNome(nome);
            usuarioParaAtualizar.setSobrenome(sobrenome);
            usuarioParaAtualizar.setEmail(email);
            if (senha != null && !senha.trim().isEmpty()) {
                usuarioParaAtualizar.setSenha(senha);
            }
            usuarioParaAtualizar.setTipo(tipo);

            int resultado = dao.update(usuarioParaAtualizar);

            if (resultado > 0) {
                success = true;
            } else {
                erro = "Não foi possível atualizar (ID: " + id + ").";
            }

        } catch (IllegalArgumentException | NullPointerException e) {
            erro = "Erro de validação: " + e.getMessage();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                erro = "Erro: Já existe um usuário com este e-mail.";
            } else {
                erro = "Erro de banco de dados ao atualizar: " + e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao atualizar: " + e.getMessage();
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

        List<Usuario> listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = dao.read();
        } catch (SQLException readEx) {
            readEx.printStackTrace();
        }
        request.setAttribute("listaUsuarios", listaUsuarios);

        if (id > 0) {
            try {
                request.setAttribute("usuarioModal", dao.readById(id));
            } catch (Exception e) {
            }
        }

        request.setAttribute("abrirModal", "update");
        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}