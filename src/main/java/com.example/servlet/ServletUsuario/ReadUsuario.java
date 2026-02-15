package com.example.servlet.ServletUsuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Usuario;
import com.example.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios-crud")
public class ReadUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO dao = new UsuarioDAO();
        String pk = request.getParameter("pk");

        if (pk != null && !pk.isEmpty()) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                int id = Integer.parseInt(pk);
                Usuario usuario = dao.readById(id);

                if (usuario != null) {
                    String json = "{"
                            + "\"id\":\"" + id + "\","
                            + "\"nome\":\"" + escapeJson(usuario.getNome()) + "\","
                            + "\"sobrenome\":\"" + escapeJson(usuario.getSobrenome()) + "\","
                            + "\"email\":\"" + escapeJson(usuario.getEmail()) + "\","
                            + "\"tipo\":\"" + escapeJson(usuario.getTipo()) + "\""
                            + "}";

                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"erro\":\"Usuário ID " + pk + " não encontrado.\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"erro\":\"PK inválida: " + pk + "\"}");
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"erro\":\"Erro de banco: " + e.getMessage() + "\"}");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"erro\":\"Erro inesperado: " + e.getMessage() + "\"}");
            }

        } else {

            List<Usuario> listaUsuarios = new ArrayList<>();
            String erro = null;

            String nomePesquisa = request.getParameter("pesquisa");
            String ordem = request.getParameter("ordem");

            String direction = ("decrescente".equalsIgnoreCase(ordem)) ? "DESC" : "ASC";
            String orderBy = "id";

            if ("nome".equalsIgnoreCase(request.getParameter("coluna"))) orderBy = "nome";
            else if ("email".equalsIgnoreCase(request.getParameter("coluna"))) orderBy = "email";

            try {
                if (nomePesquisa != null && !nomePesquisa.isEmpty()) {
                    listaUsuarios = dao.read();
                } else {
                    listaUsuarios = dao.read();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                erro = "Erro ao buscar dados do banco: " + e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                erro = "Erro inesperado ao carregar dados: " + e.getMessage();
            }

            request.setAttribute("listaUsuarios", listaUsuarios);

            if (erro != null) {
                request.setAttribute("erro", erro);
            }

            request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
        }
    }

    private String escapeJson(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\"", "\\\"");
    }
}