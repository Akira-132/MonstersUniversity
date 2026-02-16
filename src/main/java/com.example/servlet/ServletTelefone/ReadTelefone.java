package com.example.servlet.ServletTelefone;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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

            request.setAttribute("listaUsuarios", usuarioDAO.read());

            if ("prepararUpdate".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Telefone t = telefoneDAO.readById(id);
                if (t != null) {
                    request.setAttribute("telefoneModal", t);
                    request.setAttribute("modalAtivo", "update");
                }
            }
            else if ("prepararDelete".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Telefone t = telefoneDAO.readById(id);
                if (t != null) {
                    request.setAttribute("telefoneModal", t);
                    request.setAttribute("modalAtivo", "delete");
                }
            }
            else if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao processar dados: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}