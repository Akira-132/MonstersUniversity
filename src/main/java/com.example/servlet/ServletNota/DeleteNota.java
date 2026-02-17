package com.example.servlet.ServletNota;

import com.example.dao.NotaDAO;
import com.example.models.Nota;
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

        NotaDAO dao = new NotaDAO();
        String idStr = request.getParameter("id");
        boolean success = false;
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            success = dao.deleteById(id) > 0;
        } catch (Exception e) {
            erro = e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/nota-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");
        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}