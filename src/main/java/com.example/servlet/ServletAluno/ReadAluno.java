package com.example.servlet.ServletAluno;

import java.io.IOException;

import com.example.models.Aluno;
import com.example.models.Turma;
import com.example.models.Usuario;
import com.example.dao.AlunoDAO;
import com.example.dao.AdminDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.TurmaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/aluno-read")
public class ReadAluno extends HttpServlet {

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
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");

        if (idStr == null) {
            response.sendRedirect(request.getContextPath() + "/turma-read");
            return;
        }

        try {

            int id = Integer.parseInt(idStr);

            AlunoDAO alunoDAO = new AlunoDAO();
            Aluno alunoSelecionado = alunoDAO.readById(id);

            if (alunoSelecionado == null) {
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            }

            request.setAttribute("alunoAtual", alunoSelecionado);

            AdminDAO adminDAO = new AdminDAO();
            ProfessorDAO professorDAO = new ProfessorDAO();

            boolean ehAdmin = adminDAO.readByUsuarioId(usuarioLogado.getId()) != null;
            boolean ehProfessor = professorDAO.readByUsuarioId(usuarioLogado.getId()) != null;

            if (ehAdmin) {
                String idTurmaStr = request.getParameter("idTurma");
                if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
                    request.setAttribute("idTurmaAtual", idTurmaStr);
                }
                request.getRequestDispatcher("/WEB-INF/views/diogoAdm.jsp")
                        .forward(request, response);

            } else if (ehProfessor) {
                String idTurmaStr = request.getParameter("idTurma");
                if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
                    request.setAttribute("idTurmaAtual", idTurmaStr);
                }
                request.getRequestDispatcher("/WEB-INF/views/diogo.jsp")
                        .forward(request, response);

            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/turma-read");
        }
    }
}