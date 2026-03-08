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
        String idTurmaStr = request.getParameter("idTurma");

        NotaDAO notaDAO = new NotaDAO();
        String erro = null;

        try {

            int id = Integer.parseInt(idStr);
            int semestre = Integer.parseInt(semestreStr);
            int ano = Integer.parseInt(anoStr);
            double valor = Double.parseDouble(notaValorStr.replace(",", "."));
            int fkAlunoId = Integer.parseInt(idAlunoStr);
            int fkDisciplinaId = Integer.parseInt(idDisciplinaStr);

            if (!tipo.equals("N1") && !tipo.equals("N2")) {
                throw new IllegalArgumentException("Tipo deve ser N1 ou N2.");
            }
            if (semestre != 1 && semestre != 2) {
                throw new IllegalArgumentException("Semestre deve ser 1 ou 2.");
            }

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

                String redirecionamento = request.getContextPath() + "/nota-read?sucesso=ok&idDisciplina=" + fkDisciplinaId;
                if (idTurmaStr != null && !idTurmaStr.equals("0")) {
                    redirecionamento += "&idTurma=" + idTurmaStr;
                }
                response.sendRedirect(redirecionamento);
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

        String urlErro = request.getContextPath()
                + "/nota-read?acao=prepararUpdate&id=" + idStr;

        if (idDisciplinaStr != null) urlErro += "&idDisciplina=" + idDisciplinaStr;
        if (idTurmaStr != null)      urlErro += "&idTurma=" + idTurmaStr;

        response.sendRedirect(urlErro);
    }
}