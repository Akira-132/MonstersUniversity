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

@WebServlet("/telefone-create")
public class CreateTelefone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String numero = request.getParameter("telefone");
        String idUsuarioStr = request.getParameter("fkUsuarioId");

        TelefoneDAO telefoneDAO = new TelefoneDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            int fkUsuarioId = Integer.parseInt(idUsuarioStr);

            Telefone novoTelefone = new Telefone(numero, fkUsuarioId);

            if (telefoneDAO.create(novoTelefone)) {
                response.sendRedirect(request.getContextPath() + "/telefone-read");
                return;
            } else {
                erro = "Erro ao cadastrar telefone no banco.";
            }

        } catch (NumberFormatException e) {
            erro = "Selecione um usuário válido.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao cadastrar telefone.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");
        request.setAttribute("telefone_previo", numero);

        try {
            request.setAttribute("listaTelefones", telefoneDAO.read());
            request.setAttribute("listaUsuarios", usuarioDAO.read());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro crítico: Não foi possível carregar as listas.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}