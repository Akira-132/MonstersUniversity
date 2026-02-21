package com.example.servlet.ServletUsuario;

import java.io.IOException;
import java.util.List;
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

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Usuario> lista = usuarioDAO.read();
            request.setAttribute("listaUsuarios", lista);

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Usuario usuario = usuarioDAO.readById(id);

                if (usuario != null) {
                    request.setAttribute("usuarioModal", usuario);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar usuários.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
    }
}