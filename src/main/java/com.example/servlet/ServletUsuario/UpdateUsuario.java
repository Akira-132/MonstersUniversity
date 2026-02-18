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

@WebServlet("/usuario-update")
public class UpdateUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String tipo = request.getParameter("tipo");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            Usuario usuario = usuarioDAO.readById(id);

            if (usuario == null) throw new Exception("Usuário não encontrado.");

            usuario.setNome(nome);
            usuario.setSobrenome(sobrenome);
            usuario.setEmail(email);
            usuario.setTipo(tipo);

            if (senha != null && !senha.trim().isEmpty()) {
                usuario.setSenha(senha);
            }

            if (usuarioDAO.update(usuario) > 0) {
                response.sendRedirect(request.getContextPath() + "/usuario-read");
                return;
            } else {
                erro = "Erro ao atualizar usuário no banco.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                erro = "Este e-mail já está em uso.";
            } else {
                erro = "Erro de banco de dados ao atualizar.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");

        try {
            request.setAttribute("listaUsuarios", usuarioDAO.read());

            if (idStr != null) {
                request.setAttribute("usuarioModal", usuarioDAO.readById(Integer.parseInt(idStr)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}