package com.example.servlet.ServletDisciplina;

import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.ObservacaoDAO;
import com.example.models.Aluno;
import com.example.models.Disciplina;
import com.example.models.Observacao;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/disciplina-detalhe-read")
public class ReadDisciplinaDetalhe extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        ObservacaoDAO obsDAO = new ObservacaoDAO();

        try {
            if (idStr == null || idStr.trim().isEmpty()) {
                request.setAttribute("erro", "ID da disciplina não fornecido.");
                request.getRequestDispatcher("/WEB-INF/views/disciplina-detalhe.jsp").forward(request, response);
                return;
            }

            int idDisciplina = Integer.parseInt(idStr);
            Disciplina disciplina = disciplinaDAO.readById(idDisciplina);

            if (disciplina == null) {
                request.setAttribute("erro", "Disciplina não encontrada no sistema.");
                request.getRequestDispatcher("/WEB-INF/views/disciplina-detalhe.jsp").forward(request, response);
                return;
            }

            request.setAttribute("disciplinaAtual", disciplina);

            Aluno aluno = alunoDAO.readByUsuarioId(usuarioLogado.getId());
            if (aluno != null) {
                List<Observacao> todasObs = obsDAO.readByAlunoId(aluno.getId());
                List<Observacao> obsDisc  = new ArrayList<>();
                for (Observacao o : todasObs) {
                    if (o.getFkProfessorId() == disciplina.getFkProfessorId()) {
                        obsDisc.add(o);
                    }
                }
                request.setAttribute("listaObservacoes", obsDisc);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("erro", "ID de disciplina inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar a disciplina.");
        }

        request.getRequestDispatcher("/WEB-INF/views/disciplina-detalhe.jsp").forward(request, response);
    }
}