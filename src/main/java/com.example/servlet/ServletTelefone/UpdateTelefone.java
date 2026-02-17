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

@WebServlet("/telefone-update")
public class UpdateTelefone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        TelefoneDAO dao = new TelefoneDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String idStr = request.getParameter("id");
        String telefoneStr = request.getParameter("telefone");
        String idUsuarioStr = request.getParameter("idUsuario");

        boolean success = false;
        String erro = null;
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
            int idUsuario = Integer.parseInt(idUsuarioStr);

            Telefone telefoneAtual = dao.readById(id);

            if (telefoneAtual != null) {
                telefoneAtual.setTelefone(telefoneStr);
                telefoneAtual.setFkUsuarioId(idUsuario);

                int result = dao.update(telefoneAtual);
                if (result > 0) {
                    success = true;
                } else {
                    erro = "Erro ao atualizar registro.";
                }
            } else {
                erro = "Telefone não encontrado.";
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
            response.sendRedirect(request.getContextPath() + "/telefone-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");

        try {
            request.setAttribute("listaTelefones", dao.read());
            request.setAttribute("listaUsuarios", usuarioDAO.read());
        } catch (Exception e) {}

        if (id > 0) {
            try {
                Telefone t = dao.readById(id);
                request.setAttribute("telefoneModal", t);
            } catch (Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}