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
import java.sql.SQLException;

@WebServlet("/nota-create")
public class CreateNota extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String tipo = request.getParameter("tipo");
        String semestreStr = request.getParameter("semestre");
        String anoStr = request.getParameter("ano");
        String notaValStr = request.getParameter("nota");
        String fkAlunoIdStr = request.getParameter("fkAlunoId");
        String fkDisciplinaIdStr = request.getParameter("fkDisciplinaId");

        NotaDAO notaDAO = new NotaDAO();
        boolean success = false;
        String erro = null;

        try {
            int semestre = Integer.parseInt(semestreStr);
            int ano = Integer.parseInt(anoStr);
            double notaVal = Double.parseDouble(notaValStr);
            int fkAlunoId = Integer.parseInt(fkAlunoIdStr);
            int fkDisciplinaId = Integer.parseInt(fkDisciplinaIdStr);

            Nota novaNota = new Nota(tipo, semestre, ano, notaVal, fkAlunoId, fkDisciplinaId);
            success = notaDAO.create(novaNota);

            if (!success) erro = "Erro ao registrar nota.";

        } catch (Exception e) {
            erro = "Erro: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/nota-read");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("tipo_previo", tipo);
        request.setAttribute("semestre_previo", semestreStr);
        request.setAttribute("ano_previo", anoStr);
        request.setAttribute("nota_previo", notaValStr);
        request.setAttribute("fkAlunoId_previo", fkAlunoIdStr);
        request.setAttribute("fkDisciplinaId_previo", fkDisciplinaIdStr);
        request.setAttribute("modalAtivo", "create");

        try {
            request.setAttribute("listaNotas", notaDAO.read());
            request.setAttribute("listaAlunos", new AlunoDAO().read());
            request.setAttribute("listaDisciplinas", new DisciplinaDAO().read());
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}