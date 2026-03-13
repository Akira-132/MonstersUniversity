package com.example.servlet.ServletObservacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.models.Observacao;
import com.example.models.Aluno;
import com.example.models.Usuario;
import com.example.dao.ObservacaoDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/observacao-read")
public class ReadObservacao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        String idAlunoStr = request.getParameter("idAluno");
        String idObsStr = request.getParameter("id");

        try {
            boolean isProfessor = false;
            if (usuarioLogado != null && professorDAO.readByUsuarioId(usuarioLogado.getId()) != null) {
                isProfessor = true;
            }

            if (idObsStr != null) {
                int idObs = Integer.parseInt(idObsStr);
                Observacao obs = observacaoDAO.readById(idObs);

                if (obs != null) {
                    request.setAttribute("observacao", obs);
                    request.setAttribute("alunoAtual", alunoDAO.readById(obs.getFkAlunoId()));
                    String idTurmaStr = request.getParameter("idTurma");
                    if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
                        request.setAttribute("idTurmaAtual", idTurmaStr);
                    }
                }

                if (isProfessor) {
                    request.getRequestDispatcher("/WEB-INF/views/observacao-detalhe-prof.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/observacao-detalhe-adm.jsp").forward(request, response);
                }
                return;

            } else if (idAlunoStr != null) {
                int idAluno = Integer.parseInt(idAlunoStr);
                Aluno aluno = alunoDAO.readById(idAluno);

                List<Observacao> doAluno = observacaoDAO.readByAlunoId(idAluno);
                if (doAluno == null) doAluno = new ArrayList<>();

                request.setAttribute("alunoAtual", aluno);
                request.setAttribute("listaObservacoes", doAluno);
                String idTurmaStr = request.getParameter("idTurma");
                if (idTurmaStr != null && !idTurmaStr.isEmpty()) {
                    request.setAttribute("idTurmaAtual", idTurmaStr);
                }

                if (isProfessor) {
                    request.getRequestDispatcher("/WEB-INF/views/historico-prof.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/historico-adm.jsp").forward(request, response);
                }
                return;
            }

            response.sendRedirect(request.getContextPath() + "/turma-read");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/turma-read?erro=carregamento");
        }
    }
}