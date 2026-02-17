package com.example.servlet.ServletNota;

import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.NotaDAO;
import com.example.models.Nota;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/nota-update")
public class UpdateNota extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        NotaDAO notaDAO = new NotaDAO();

        String idStr = request.getParameter("id");
        String tipo = request.getParameter("tipo");
        String semestreStr = request.getParameter("semestre");
        String anoStr = request.getParameter("ano");
        String notaValStr = request.getParameter("nota");
        String fkAlunoIdStr = request.getParameter("fkAlunoId");
        String fkDisciplinaIdStr = request.getParameter("fkDisciplinaId");

        boolean success = false;
        String erro = null;
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
            Nota notaAtual = notaDAO.readById(id);

            if (notaAtual != null) {
                notaAtual.setTipo(tipo);
                notaAtual.setSemestre(Integer.parseInt(semestreStr));
                notaAtual.setAno(Integer.parseInt(anoStr));
                notaAtual.setNota(Double.parseDouble(notaValStr));
                notaAtual.setFkAlunoId(Integer.parseInt(fkAlunoIdStr));
                notaAtual.setFkDisciplinaId(Integer.parseInt(fkDisciplinaIdStr));

                success = notaDAO.update(notaAtual) > 0;
            } else {
                erro = "Nota não encontrada.";
            }
        } catch (Exception e) {
            erro = e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/nota-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");
        try {
            request.setAttribute("listaNotas", notaDAO.read());
            request.setAttribute("listaAlunos", new AlunoDAO().read());
            request.setAttribute("listaDisciplinas", new DisciplinaDAO().read());
            if (id > 0) request.setAttribute("notaModal", notaDAO.readById(id));
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}