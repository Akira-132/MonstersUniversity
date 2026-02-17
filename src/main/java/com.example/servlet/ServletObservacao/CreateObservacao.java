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
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/observacao-create")
public class CreateObservacao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String texto = request.getParameter("texto");
        String fkProfessorIdStr = request.getParameter("fkProfessorId");
        String fkAlunoIdStr = request.getParameter("fkAlunoId");

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        boolean success = false;
        String erro = null;

        try {
            int fkProfessorId = Integer.parseInt(fkProfessorIdStr);
            int fkAlunoId = Integer.parseInt(fkAlunoIdStr);

            Observacao novaObservacao = new Observacao(texto, fkProfessorId, fkAlunoId);

            success = observacaoDAO.create(novaObservacao);

            if (!success) {
                erro = "Erro ao registrar observação.";
            }

        } catch (NumberFormatException | NullPointerException e) {
            erro = "Erro de validação: Professor ou Aluno inválido.";

        } catch (IllegalArgumentException e) {
            erro = "Erro de validação: " + e.getMessage();

        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/observacao-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("texto_previo", texto);
        request.setAttribute("fkProfessorId_previo", fkProfessorIdStr);
        request.setAttribute("fkAlunoId_previo", fkAlunoIdStr);

        try {
            request.setAttribute("listaObservacoes", observacaoDAO.read());

            ProfessorDAO professorDAO = new ProfessorDAO();
            request.setAttribute("listaProfessores", professorDAO.read());

            AlunoDAO alunoDAO = new AlunoDAO();
            request.setAttribute("listaAlunos", alunoDAO.read());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("modalAtivo", "create");
        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
    }
}