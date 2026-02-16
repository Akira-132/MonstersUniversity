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

@WebServlet("/usuario-delete")
public class DeleteUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO dao = new UsuarioDAO();
        int id = 0;
        boolean success = false;
        String erro = null;

        try {
            String idParam = request.getParameter("id");
            id = Integer.parseInt(idParam);

            int resultado = dao.deleteById(id);

            if (resultado > 0) {
                success = true;
            } else {
                erro = "Não foi possível deletar o usuário.";
            }

        } catch (NumberFormatException e) {
            erro = "ID inválido.";
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("violates foreign key constraint")) {
                erro = "Não é possível excluir: Usuário vinculado a outros registros (Aluno, Professor, etc).";
            } else {
                erro = "Erro de banco: " + e.getMessage();
            }
        } catch (Exception e) {
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/usuario-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

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