package com.example.servlet.ServletNota;

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

        String idStr = request.getParameter("id");
        String tipo = request.getParameter("tipo");
        String semestreStr = request.getParameter("semestre");
        String anoStr = request.getParameter("ano");
        String notaValorStr = request.getParameter("nota");
        String idAlunoStr = request.getParameter("fkAlunoId");
        String idDisciplinaStr = request.getParameter("fkDisciplinaId");

        NotaDAO notaDAO = new NotaDAO();
        String erro = null;

        try {

            int id = Integer.parseInt(idStr);
            int semestre = Integer.parseInt(semestreStr);
            int ano = Integer.parseInt(anoStr);
            double valor = Double.parseDouble(notaValorStr.replace(",", "."));
            int fkAlunoId = Integer.parseInt(idAlunoStr);
            int fkDisciplinaId = Integer.parseInt(idDisciplinaStr);

            Nota nota = notaDAO.readById(id);

            if (nota == null) {
                throw new Exception("Nota não encontrada.");
            }

            nota.setTipo(tipo);
            nota.setSemestre(semestre);
            nota.setAno(ano);
            nota.setNota(valor);
            nota.setFkAlunoId(fkAlunoId);
            nota.setFkDisciplinaId(fkDisciplinaId);

            if (notaDAO.update(nota) > 0) {

                // REDIRECIONA PARA O READ DA DISCIPLINA
                response.sendRedirect(
                        request.getContextPath() +
                                "/nota-read?idDisciplina=" + fkDisciplinaId
                );
                return;

            } else {
                erro = "Erro ao atualizar nota no banco.";
            }

        } catch (NumberFormatException e) {
            erro = "Valores numéricos inválidos.";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro: " + e.getMessage();
        }

        request.setAttribute("erro", erro);

        request.getRequestDispatcher(
                "/nota-read?acao=prepararUpdate&id=" + idStr
        ).forward(request, response);
    }
}