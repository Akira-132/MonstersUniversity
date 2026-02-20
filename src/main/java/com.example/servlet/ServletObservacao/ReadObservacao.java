package com.example.servlet.ServletObservacao;

import java.io.IOException;
import java.util.List;
import com.example.models.Observacao;
import com.example.dao.ObservacaoDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/observacao-read")
public class ReadObservacao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Observacao> lista = observacaoDAO.read();
            request.setAttribute("listaObservacoes", lista);

            if ("prepararCreate".equals(acao) || "prepararUpdate".equals(acao)) {
                request.setAttribute("listaAlunos", alunoDAO.read());
                request.setAttribute("listaProfessores", professorDAO.read());
            }

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Observacao observacao = observacaoDAO.readById(id);

                if (observacao != null) {
                    request.setAttribute("observacaoModal", observacao);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar dados.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
    }
}