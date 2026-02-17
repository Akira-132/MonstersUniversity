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
        response.sendRedirect(request.getContextPath() + "/usuario-read");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        UsuarioDAO dao = new UsuarioDAO();

        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String tipo = request.getParameter("tipo");

        boolean success = false;
        String erro = null;
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
            Usuario usuarioAtual = dao.readById(id);

            if (usuarioAtual != null) {
                usuarioAtual.setNome(nome);
                usuarioAtual.setSobrenome(sobrenome);
                usuarioAtual.setEmail(email);
                usuarioAtual.setTipo(tipo);

                if (senha != null && !senha.trim().isEmpty()) {
                    usuarioAtual.setSenha(senha);
                }

                int result = dao.update(usuarioAtual);
                if (result > 0) {
                    success = true;
                } else {
                    erro = "Erro ao atualizar registro.";
                }
            } else {
                erro = "Usuário não encontrado.";
            }

        } catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("UNIQUE")) {
                erro = "E-mail já cadastrado.";
            } else {
                erro = "Erro de banco: " + e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/usuario-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("nome_previo", nome);
        request.setAttribute("sobrenome_previo", sobrenome);
        request.setAttribute("email_previo", email);
        request.setAttribute("tipo_previo", tipo);
        request.setAttribute("modalAtivo", "update");

        List<Usuario> lista = new ArrayList<>();
        try { lista = dao.read(); } catch (Exception e) {}
        request.setAttribute("listaUsuarios", lista);

        if (id > 0) {
            try {
                Usuario u = dao.readById(id);
                if(u != null) request.setAttribute("usuarioModal", u);
            } catch (Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}