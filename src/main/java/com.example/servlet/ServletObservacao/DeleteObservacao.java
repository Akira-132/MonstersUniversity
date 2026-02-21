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

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (observacaoDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/observacao-read");
                return;
            } else {
                erro = "Não foi possível excluir a observação.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao excluir.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaObservacoes", observacaoDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("observacaoModal", observacaoDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
    }
}