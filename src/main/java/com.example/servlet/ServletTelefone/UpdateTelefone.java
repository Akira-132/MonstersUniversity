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

@WebServlet("/telefone-update")
public class UpdateTelefone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String numero = request.getParameter("telefone");
        String idUsuarioStr = request.getParameter("fkUsuarioId");

        TelefoneDAO telefoneDAO = new TelefoneDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            int fkUsuarioId = Integer.parseInt(idUsuarioStr);

            Telefone telefone = telefoneDAO.readById(id);
            if (telefone == null) throw new Exception("Telefone não encontrado.");

            telefone.setTelefone(numero);
            telefone.setFkUsuarioId(fkUsuarioId);

            if (telefoneDAO.update(telefone) > 0) {
                response.sendRedirect(request.getContextPath() + "/telefone-read");
                return;
            } else {
                erro = "Erro ao atualizar telefone no banco.";
            }

        } catch (NumberFormatException e) {
            erro = "Dados inválidos.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");

        try {
            request.setAttribute("listaTelefones", telefoneDAO.read());
            request.setAttribute("listaUsuarios", usuarioDAO.read());

            if (idStr != null) {
                request.setAttribute("telefoneModal", telefoneDAO.readById(Integer.parseInt(idStr)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar as listas.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}