package com.example.servlet.ServletNota;

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
        String idTurmaStr = request.getParameter("idTurma");

        NotaDAO notaDAO = new NotaDAO();
        String erro = null;

        try {
            int semestre = Integer.parseInt(semestreStr);
            int ano = Integer.parseInt(anoStr);
            if (notaValorStr == null || notaValorStr.trim().isEmpty()) {
                throw new IllegalArgumentException("O campo nota não pode estar vazio.");
            }
            double valor = Double.parseDouble(notaValorStr.replace(",", "."));
            int fkAlunoId = Integer.parseInt(idAlunoStr);
            int fkDisciplinaId = Integer.parseInt(idDisciplinaStr);

            Nota novaNota = new Nota(tipo, semestre, ano, valor, fkAlunoId, fkDisciplinaId);

            if (notaDAO.create(novaNota)) {
                String redirecionamento = request.getContextPath() + "/nota-read?sucesso=ok&idDisciplina=" + fkDisciplinaId;
                if (idTurmaStr != null && !idTurmaStr.equals("0")) {
                    redirecionamento += "&idTurma=" + idTurmaStr;
                }
                response.sendRedirect(redirecionamento);
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

        request.getRequestDispatcher("/nota-read?acao=prepararCreate").forward(request, response);
    }
}