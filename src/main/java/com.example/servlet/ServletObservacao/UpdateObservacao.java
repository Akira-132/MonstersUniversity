package com.example.servlet.ServletObservacao;

import com.example.dao.ObservacaoDAO;
import com.example.models.Observacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.ZoneId;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/observacao-update")
public class UpdateObservacao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String texto = request.getParameter("texto");
        String idProfessorStr = request.getParameter("fkProfessorId");
        String idAlunoStr = request.getParameter("fkAlunoId");

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            int fkProfessorId = Integer.parseInt(idProfessorStr);
            int fkAlunoId = Integer.parseInt(idAlunoStr);

            Observacao observacao = observacaoDAO.readById(id);
            if (observacao == null) throw new Exception("Observação não encontrada.");

            observacao.setComentario(texto);
            observacao.setFkProfessorId(fkProfessorId);
            observacao.setFkAlunoId(fkAlunoId);
            observacao.setDataEnvio(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

            if (observacaoDAO.update(observacao) > 0) {
                response.sendRedirect(request.getContextPath() + "/observacao-read?idAluno=" + fkAlunoId
                + (request.getParameter("idTurma") != null ? "&idTurma=" + request.getParameter("idTurma") : ""));
                return;
            } else {
                erro = "Erro ao atualizar observação no banco.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        response.sendRedirect(request.getContextPath() + "/observacao-read?id=" + idStr + "&erro=update");
    }
}