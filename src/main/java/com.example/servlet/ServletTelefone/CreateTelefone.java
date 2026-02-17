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

@WebServlet("/telefone-create")
public class CreateTelefone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String telefoneStr = request.getParameter("telefone");
        String idUsuarioStr = request.getParameter("idUsuario");

        TelefoneDAO dao = new TelefoneDAO();
        boolean success = false;
        String erro = null;

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            Telefone novoTelefone = new Telefone(telefoneStr, idUsuario);

            success = dao.create(novoTelefone);

            if (!success) {
                erro = "Erro ao cadastrar telefone.";
            }

        } catch (NumberFormatException | NullPointerException e) {
            erro = "Erro: Selecione um usuário válido.";

        } catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();

        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/telefone-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("telefone_previo", telefoneStr);
        request.setAttribute("idUsuario_previo", idUsuarioStr);

        try {
            request.setAttribute("listaTelefones", dao.read());
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            request.setAttribute("listaUsuarios", usuarioDAO.read());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("modalAtivo", "create");
        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}