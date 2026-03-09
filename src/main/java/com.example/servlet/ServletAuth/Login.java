package com.example.servlet.ServletAuth;

import com.example.dao.AlunoDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String senha = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        try {
            Usuario usuarioLogado = null;

            if (username.matches("\\d+")) {
                usuarioLogado = alunoDAO.loginPorMatricula(username, senha);

                if (usuarioLogado != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogado", usuarioLogado);
                    session.setAttribute("role", "aluno");
                    response.sendRedirect(request.getContextPath() + "/boletim-read");
                    return;
                }
            } else {
                usuarioLogado = usuarioDAO.login(username, senha);

                if (usuarioLogado != null && professorDAO.readByUsuarioId(usuarioLogado.getId()) != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogado", usuarioLogado);
                    session.setAttribute("role", "professor");
                    response.sendRedirect(request.getContextPath() + "/turma-read");
                    return;
                }
            }

            request.setAttribute("erro", "Usuário ou senha incorretos.");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao tentar fazer login.");
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}