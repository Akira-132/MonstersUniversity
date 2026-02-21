package com.example.servlet.ServletObservacao;

import com.example.dao.AlunoDAO;
import com.example.dao.ObservacaoDAO;
import com.example.dao.ProfessorDAO;
import com.example.models.Observacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/observacao-create")
public class CreateObservacao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String texto = request.getParameter("texto");
        String idProfessorStr = request.getParameter("fkProfessorId");
        String idAlunoStr = request.getParameter("fkAlunoId");

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        String erro = null;

        try {
            int fkProfessorId = Integer.parseInt(idProfessorStr);
            int fkAlunoId = Integer.parseInt(idAlunoStr);
            Observacao novaObservacao = new Observacao(texto, fkProfessorId, fkAlunoId);

            if (observacaoDAO.create(novaObservacao)) {
                response.sendRedirect(request.getContextPath() + "/observacao-read");
                return;
            } else {
                erro = "Erro ao registrar observação no banco de dados.";
            }

        } catch (NumberFormatException e) {
            erro = "Selecione um aluno e um professor válidos.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao registrar observação.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");
        request.setAttribute("texto_previo", texto);

        try {
            request.setAttribute("listaObservacoes", observacaoDAO.read());
            request.setAttribute("listaAlunos", alunoDAO.read());
            request.setAttribute("listaProfessores", professorDAO.read());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro crítico: Não foi possível carregar as listas.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
    }
}