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

@WebServlet("/nota-create")
public class CreateNota extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String tipo = request.getParameter("tipo");
        String semestreStr = request.getParameter("semestre");
        String anoStr = request.getParameter("ano");
        String notaValorStr = request.getParameter("nota");
        String idAlunoStr = request.getParameter("fkAlunoId");
        String idDisciplinaStr = request.getParameter("fkDisciplinaId");

        NotaDAO notaDAO = new NotaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String erro = null;

        try {
            int semestre = Integer.parseInt(semestreStr);
            int ano = Integer.parseInt(anoStr);
            double valor = Double.parseDouble(notaValorStr.replace(",", "."));
            int fkAlunoId = Integer.parseInt(idAlunoStr);
            int fkDisciplinaId = Integer.parseInt(idDisciplinaStr);

            Nota novaNota = new Nota(tipo, semestre, ano, valor, fkAlunoId, fkDisciplinaId);

            if (notaDAO.create(novaNota)) {
                response.sendRedirect(request.getContextPath() + "/nota-read");
                return;
            } else {
                erro = "Erro ao lançar nota no banco de dados.";
            }

        } catch (NumberFormatException e) {
            erro = "Verifique os valores numéricos digitados.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao lançar nota.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");
        request.setAttribute("tipo_previo", tipo);
        request.setAttribute("semestre_previo", semestreStr);
        request.setAttribute("ano_previo", anoStr);
        request.setAttribute("nota_previo", notaValorStr);

        try {
            request.setAttribute("listaNotas", notaDAO.read());
            request.setAttribute("listaAlunos", alunoDAO.read());
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro crítico: Não foi possível carregar as listas.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}