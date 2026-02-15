package com.example.servlet.ServletTelefone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Telefone;
import com.example.dao.TelefoneDAO;
import com.example.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/telefones-crud")
public class ReadTelefone extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TelefoneDAO dao = new TelefoneDAO();
        String pk = request.getParameter("pk");

        if (pk != null && !pk.isEmpty()) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                int id = Integer.parseInt(pk);
                Telefone telefone = dao.readById(id);

                if (telefone != null) {
                    String json = "{"
                            + "\"id\":\"" + id + "\","
                            + "\"telefone\":\"" + escapeJson(telefone.getTelefone()) + "\","
                            + "\"idUsuario\":\"" + telefone.getFkUsuarioId() + "\""
                            + "}";

                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"erro\":\"Telefone ID " + pk + " não encontrado.\"}");
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

            List<Telefone> listaTelefones = new ArrayList<>();
            String erro = null;
            String filtroUsuario = request.getParameter("filtroUsuario");

            try {
                if (filtroUsuario != null && !filtroUsuario.isEmpty()) {
                    int idUsuario = Integer.parseInt(filtroUsuario);
                    listaTelefones = dao.readByUsuarioId(idUsuario);
                } else {
                    listaTelefones = dao.read();
                }

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                request.setAttribute("listaUsuarios", usuarioDAO.read());

            } catch (SQLException e) {
                e.printStackTrace();
                erro = "Erro ao buscar dados: " + e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                erro = "Erro inesperado: " + e.getMessage();
            }

            request.setAttribute("listaTelefones", listaTelefones);
            if (erro != null) {
                request.setAttribute("erro", erro);
            }

            request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}