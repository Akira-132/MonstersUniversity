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
import java.util.ArrayList;

@WebServlet("/admin-update")
public class UpdateAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin-read");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdminDAO adminDAO = new AdminDAO();

        String idAdminStr = request.getParameter("id");
        String idUsuarioStr = request.getParameter("idUsuario");

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        boolean success = false;
        String erro = null;
        int idAdmin = 0;

        try {
            idAdmin = Integer.parseInt(idAdminStr);
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
            response.sendRedirect(request.getContextPath() + "/admin-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("modalAtivo", "update");

        List<Admin> listaAdmins = new ArrayList<>();
        try {
            listaAdmins = adminDAO.read();
        } catch (Exception e) {}
        request.setAttribute("listaAdmins", listaAdmins);

        if (idAdmin > 0) {
            try {
                Admin a = adminDAO.readById(idAdmin);
                if(a != null) {
                    Usuario u = usuarioDAO.readById(a.getFkUsuarioId());
                    a.setUsuario(u);
                    request.setAttribute("adminModal", a);
                }
            } catch(Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/admins.jsp").forward(request, response);
    }
}