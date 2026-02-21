package com.example.servlet.ServletAuth;

import com.example.dao.AdminDAO;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdminDAO adminDAO = new AdminDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        try {
            Usuario usuarioLogado = usuarioDAO.login(email, senha);

            if (usuarioLogado != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuarioLogado);

                if (adminDAO.readByUsuarioId(usuarioLogado.getId()) != null) {
                    response.sendRedirect(request.getContextPath() + "/admin-read");
                    return;
                }

                if (professorDAO.readByUsuarioId(usuarioLogado.getId()) != null) {
                    response.sendRedirect(request.getContextPath() + "/disciplina-read");
                    return;
                }

                if (alunoDAO.readByUsuarioId(usuarioLogado.getId()) != null) {
                    response.sendRedirect(request.getContextPath() + "/nota-read");
                    return;
                }

                request.setAttribute("erro", "Perfil de acesso não encontrado.");
            } else {
                request.setAttribute("erro", "Usuário ou senha incorretos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao tentar fazer login.");
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}