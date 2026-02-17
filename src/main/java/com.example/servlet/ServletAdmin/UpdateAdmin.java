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
import java.util.List;

@WebServlet("/admin-update")
public class UpdateAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idAdminStr = request.getParameter("id");
        String idUsuarioStr = request.getParameter("idUsuario");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdminDAO adminDAO = new AdminDAO();
        String erro = null;

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            Usuario usuario = usuarioDAO.readById(idUsuario);

            if (usuario == null) {
                throw new SQLException("Usuário original não encontrado.");
            }

            usuario.setNome(nome);
            usuario.setSobrenome(sobrenome);
            usuario.setEmail(email);

            if (senha != null && !senha.trim().isEmpty()) {
                usuario.setSenha(senha);
            }

            if (usuarioDAO.update(usuario) > 0) {
                response.sendRedirect(request.getContextPath() + "/admin-read");
                return;
            } else {
                erro = "Não foi possível atualizar o banco de dados.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("UNIQUE")) {
                erro = "Este e-mail já pertence a outro usuário.";
            } else {
                erro = "Erro ao atualizar dados no banco.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao atualizar.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);

        try {
            List<Admin> lista = adminDAO.read();
            for (Admin a : lista) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
            request.setAttribute("listaAdmins", lista);

            if (idAdminStr != null) {
                int idAdmin = Integer.parseInt(idAdminStr);
                Admin a = adminDAO.readById(idAdmin);
                if (a != null) {
                    a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                    request.setAttribute("adminModal", a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (erro == null) request.setAttribute("erro", "Erro inesperado ao recarregar a lista.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}