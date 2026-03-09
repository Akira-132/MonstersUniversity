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
        String idDisciplinaStr = request.getParameter("idDisciplina");
        String idTurmaStr = request.getParameter("idTurma");
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (notaDAO.deleteById(id) > 0) {
                String redirecionamento = request.getContextPath() + "/nota-read?sucesso=ok";
                if (idDisciplinaStr != null && !idDisciplinaStr.equals("0")) {
                    redirecionamento += "&idDisciplina=" + idDisciplinaStr;
                    if (idTurmaStr != null && !idTurmaStr.equals("0")) {
                        redirecionamento += "&idTurma=" + idTurmaStr;
                    }
                }
                response.sendRedirect(redirecionamento);
                return;
            } else {
                erro = "Não foi possível excluir a nota.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao excluir.";
        }

        request.getSession().setAttribute("erro", erro);

        String urlErro = request.getContextPath() + "/nota-read";
        if (idDisciplinaStr != null && !idDisciplinaStr.equals("0")) {
            urlErro += "?idDisciplina=" + idDisciplinaStr;
        }
        response.sendRedirect(urlErro);
    }
}