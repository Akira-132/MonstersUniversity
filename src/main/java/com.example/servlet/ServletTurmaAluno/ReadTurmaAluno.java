package com.example.servlet.ServletTurmaAluno;

import com.example.dao.AlunoDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.TurmaDAO;
import com.example.models.Turma;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/turma-aluno-read")
public class ReadTurmaAluno extends HttpServlet {

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

        String idTurmaStr = request.getParameter("id");
        if (idTurmaStr == null) idTurmaStr = request.getParameter("idTurma");

        String erroUrl = request.getParameter("erro");
        if ("falha".equals(erroUrl)) {
            request.setAttribute("erro", "Não foi possível matricular o aluno. Verifique se ele já pertence a esta turma.");
        }

        TurmaDAO turmaDAO = new TurmaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        try {
            if (idTurmaStr != null) {
                int idTurma = Integer.parseInt(idTurmaStr);

                Turma turma = turmaDAO.readById(idTurma);

                if (turma != null) {
                    request.setAttribute("turmaAtual", turma);
                    request.setAttribute("listaAlunos", turma.getAlunos());
                } else {
                    response.sendRedirect(request.getContextPath() + "/turma-read");
                    return;
                }

                request.setAttribute("listaTodosAlunos", alunoDAO.read());
            } else {
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar dados da turma.");
        }

        boolean isProfessor = false;
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            if (professorDAO.readByUsuarioId(usuarioLogado.getId()) != null) {
                isProfessor = true;
            }
        } catch (Exception e) { e.printStackTrace(); }

        if (isProfessor) {
            request.getRequestDispatcher("/WEB-INF/views/turma-professor.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/turma-adm.jsp").forward(request, response);
        }
    }
}