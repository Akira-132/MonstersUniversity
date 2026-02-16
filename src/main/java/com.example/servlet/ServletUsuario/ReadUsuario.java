package com.example.servlet.ServletUsuario;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.example.models.Usuario;
import com.example.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuario-read")
public class ReadUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO dao = new UsuarioDAO();
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Usuario> lista = dao.read();
            request.setAttribute("listaUsuarios", lista);

            if ("prepararUpdate".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Usuario u = dao.readById(id);
                if (u != null) {
                    request.setAttribute("usuarioModal", u);
                    request.setAttribute("modalAtivo", "update");
                }
            }
            else if ("prepararDelete".equals(acao) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Usuario u = dao.readById(id);
                if (u != null) {
                    request.setAttribute("usuarioModal", u);
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

        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}