package com.example.servlet.ServletObservacao;

import com.example.dao.ObservacaoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/observacao-delete")
public class DeleteObservacao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        String erro = null;
        String idStr = request.getParameter("id");

        try {
            int id = Integer.parseInt(idStr);

            if (observacaoDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            } else {
                erro = "Não foi possível excluir a observação.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao excluir.";
        }

        request.getSession().setAttribute("erro", erro);
        response.sendRedirect(request.getContextPath() + "/observacao-read?id=" + idStr);
    }
}