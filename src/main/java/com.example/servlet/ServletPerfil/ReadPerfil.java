package com.example.servlet.ServletPerfil;

import com.example.dao.AlunoDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.TelefoneDAO;
import com.example.models.Aluno;
import com.example.models.Professor;
import com.example.models.Telefone;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/perfil-read")
public class ReadPerfil extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login");
            return;
        }

        String sucessoUrl = request.getParameter("sucesso");
        if ("ok".equals(sucessoUrl)) {
            request.setAttribute("sucesso", "Dados atualizados com sucesso!");
        }

        TelefoneDAO telefoneDAO = new TelefoneDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        try {
            List<Telefone> telefones = telefoneDAO.readByUsuarioId(usuarioLogado.getId());
            if (telefones != null && !telefones.isEmpty()) {
                request.setAttribute("telefoneDoUsuario", telefones.get(0));
            }

            Professor prof = professorDAO.readByUsuarioId(usuarioLogado.getId());
            if (prof != null) {
                request.setAttribute("profLogado", prof);
                request.getRequestDispatcher("/WEB-INF/views/perfil-professor.jsp").forward(request, response);
                return;
            }

            Aluno aluno = alunoDAO.readByUsuarioId(usuarioLogado.getId());
            if (aluno != null) {
                request.setAttribute("alunoLogado", aluno);
                request.getRequestDispatcher("/WEB-INF/views/perfil-aluno.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("/WEB-INF/views/perfil-adm.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar os dados do perfil.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}