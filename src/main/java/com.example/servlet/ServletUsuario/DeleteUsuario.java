package com.example.servlet.ServletUsuario.;

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
                    request.setAttribute("abrirModal", "delete");
                } else {
                    erro = "Usuário ID " + id + " não encontrado.";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco de dados: " + e.getMessage();
        } catch (NumberFormatException e) {
            erro = "ID inválido: " + request.getParameter("id");
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
                erro = "Não foi possível deletar o usuário (ID: " + id + ").";
            }

        } catch (NumberFormatException e) {
            erro = "ID inválido fornecido para exclusão.";

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("violates foreign key constraint")) {
                erro = "Não é possível excluir este usuário, pois ele possui registros associados (ex: Aluno, Professor).";
            } else {
                erro = "Erro de banco de dados ao excluir: " + e.getMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/usuarios-crud");
            return;
        }

        request.setAttribute("erro", erro);

        List<Usuario> listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = dao.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listaUsuarios", listaUsuarios);

        if (id > 0) {
            try {
                request.setAttribute("usuarioModal", dao.readById(id));
            } catch (Exception e) {
            }
        }

        request.setAttribute("abrirModal", "delete");
        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}