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

@WebServlet("/observacao-update")
public class UpdateObservacao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/observacao-read");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        ObservacaoDAO observacaoDAO = new ObservacaoDAO();

        String idStr = request.getParameter("id");
        String texto = request.getParameter("texto");
        String fkProfessorIdStr = request.getParameter("fkProfessorId");
        String fkAlunoIdStr = request.getParameter("fkAlunoId");

        boolean success = false;
        String erro = null;
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
            int fkProfessorId = Integer.parseInt(fkProfessorIdStr);
            int fkAlunoId = Integer.parseInt(fkAlunoIdStr);

            Observacao observacaoAtual = observacaoDAO.readById(id);

            if (observacaoAtual != null) {
                observacaoAtual.setTexto(texto);
                observacaoAtual.setFkProfessorId(fkProfessorId);
                observacaoAtual.setFkAlunoId(fkAlunoId);

                int result = observacaoDAO.update(observacaoAtual);
                if (result > 0) success = true;
                else erro = "Erro ao atualizar registro.";
            } else {
                erro = "Observação não encontrada.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/observacao-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");
        request.setAttribute("texto_previo", texto);
        try {
            request.setAttribute("listaObservacoes", observacaoDAO.read());

            ProfessorDAO pDao = new ProfessorDAO();
            request.setAttribute("listaProfessores", pDao.read());

            AlunoDAO aDao = new AlunoDAO();
            request.setAttribute("listaAlunos", aDao.read());

            if (id > 0) request.setAttribute("observacaoModal", observacaoDAO.readById(id));

        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
    }
}