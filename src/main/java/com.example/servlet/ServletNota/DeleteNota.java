package com.example.servlet.ServletNota;

import com.example.dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/nota-delete")
public class DeleteNota extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NotaDAO notaDAO = new NotaDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (notaDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/nota-read");
                return;
            } else {
                erro = "Não foi possível excluir a nota.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao excluir.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaNotas", notaDAO.read());

            String idStr = request.getParameter("id");
            if (idStr != null) {
                request.setAttribute("notaModal", notaDAO.readById(Integer.parseInt(idStr)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (request.getAttribute("erro") == null) {
                request.setAttribute("erro", "Erro ao recarregar a lista.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}