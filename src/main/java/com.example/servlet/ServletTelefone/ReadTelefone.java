package com.example.servlet.ServletTelefone;

import java.io.IOException;
import java.util.List;
import com.example.models.Telefone;
import com.example.dao.TelefoneDAO;
import com.example.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/telefone-read")
public class ReadTelefone extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TelefoneDAO telefoneDAO = new TelefoneDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Telefone> lista = telefoneDAO.read();
            request.setAttribute("listaTelefones", lista);

            if ("prepararCreate".equals(acao) || "prepararUpdate".equals(acao)) {
                request.setAttribute("listaUsuarios", usuarioDAO.read());
            }

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Telefone telefone = telefoneDAO.readById(id);

                if (telefone != null) {
                    request.setAttribute("telefoneModal", telefone);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar dados.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}