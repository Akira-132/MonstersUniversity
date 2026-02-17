package com.example.servlet.ServletObservacao;

import com.example.dao.ObservacaoDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.AlunoDAO;
import com.example.models.Observacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/observacao-delete")
public class DeleteObservacao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObservacaoDAO dao = new ObservacaoDAO();
        int id = 0;
        boolean success = false;
        String erro = null;

        try {
            String idParam = request.getParameter("id");
            id = Integer.parseInt(idParam);

            int resultado = dao.deleteById(id);

            if (resultado > 0) {
                success = true;
            } else {
                erro = "Não foi possível deletar a observação.";
            }

        } catch (NumberFormatException e) {
            erro = "ID inválido.";
        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();
        } catch (Exception e) {
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/observacao-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            request.setAttribute("listaObservacoes", dao.read());
        } catch (Exception e) {}

        if (id > 0) {
            try {
                Observacao obs = dao.readById(id);
                request.setAttribute("observacaoModal", obs);
            } catch (Exception e) {}
        }

        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
    }
}