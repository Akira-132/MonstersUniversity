package com.example.servlet.ServletAuth;

import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ativar-matricula")
public class AtivacaoAluno extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/verificacaoUsuario.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idUsuarioStr = request.getParameter("idUsuario");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            Usuario usuario = usuarioDAO.readById(idUsuario);

            if (usuario != null) {
                usuario.setNome(nome);
                usuario.setSenha(senha);

                if (usuarioDAO.update(usuario) > 0) {
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                } else {
                    request.setAttribute("erro", "Erro ao ativar matrícula.");
                }
            } else {
                request.setAttribute("erro", "Sessão expirada ou usuário inválido.");
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", "Validação: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado.");
        }

        request.getRequestDispatcher("/WEB-INF/views/matricula.jsp")
                .forward(request, response);
    }
}