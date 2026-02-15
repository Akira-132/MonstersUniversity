package com.example.servlet.ServletTelefone;

import com.example.dao.TelefoneDAO;
import com.example.dao.UsuarioDAO;
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

@WebServlet("/telefone-delete")
public class DeleteTelefone extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TelefoneDAO dao = new TelefoneDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Telefone> listaTelefones = new ArrayList<>();
        String erro = null;
        String idParam = request.getParameter("id");

        try {
            listaTelefones = dao.read();
            request.setAttribute("listaUsuarios", usuarioDAO.read());

            Telefone telefoneModal = null;
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                telefoneModal = dao.readById(id);

                if (telefoneModal != null) {
                    request.setAttribute("telefoneModal", telefoneModal);
                    request.setAttribute("abrirModal", "delete");
                } else {
                    erro = "Telefone ID " + id + " não encontrado.";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();
        } catch (NumberFormatException e) {
            erro = "ID inválido: " + idParam;
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (erro != null) request.setAttribute("erro", erro);
        request.setAttribute("listaTelefones", listaTelefones);

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TelefoneDAO dao = new TelefoneDAO();
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
                erro = "Não foi possível deletar o telefone (ID: " + id + ").";
            }

        } catch (NumberFormatException e) {
            erro = "ID inválido fornecido para exclusão.";

        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco ao excluir: " + e.getMessage();

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/telefones-crud");
            return;
        }

        request.setAttribute("erro", erro);

        List<Telefone> listaTelefones = new ArrayList<>();
        try {
            listaTelefones = dao.read();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            request.setAttribute("listaUsuarios", usuarioDAO.read());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listaTelefones", listaTelefones);

        if (id > 0) {
            try { request.setAttribute("telefoneModal", dao.readById(id)); } catch (Exception e) { }
        }

        request.setAttribute("abrirModal", "delete");
        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}