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
        String urlMensagem = "";

        NotaDAO notaDAO = new NotaDAO();

        try {
            int semestre = Integer.parseInt(semestreStr);
            int ano = Integer.parseInt(anoStr);
            if (notaValorStr == null || notaValorStr.trim().isEmpty()) {
                throw new IllegalArgumentException("O campo nota não pode estar vazio.");
            }
            double valor = Double.parseDouble(notaValorStr.replace(",", "."));

            if (valor < 0 || valor > 10) {
                throw new IllegalArgumentException("O valor de nota é inválido");
            }

            int fkAlunoId = Integer.parseInt(idAlunoStr);
            int fkDisciplinaId = Integer.parseInt(idDisciplinaStr);

            Nota novaNota = new Nota(tipo, semestre, ano, valor, fkAlunoId, fkDisciplinaId);

            if (notaDAO.create(novaNota)) {
                String redirecionamento = request.getContextPath() + "/nota-read?sucesso=notaCriada&idDisciplina=" + fkDisciplinaId;
                if (idTurmaStr != null && !idTurmaStr.equals("0")) {
                    redirecionamento += "&idTurma=" + idTurmaStr;
                }
                response.sendRedirect(redirecionamento);
                return;
            }

        } catch (NumberFormatException e) {
            urlMensagem = "&mensagem=Valor+de+nota+inválido.";
        } catch (IllegalArgumentException e) {
            urlMensagem = "&mensagem=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String urlErro = request.getContextPath() + "/nota-read?erro=create&acao=prepararCreate";
        if (idDisciplinaStr != null) urlErro += "&idDisciplina=" + idDisciplinaStr;
        if (idTurmaStr != null && !idTurmaStr.equals("0")) urlErro += "&idTurma=" + idTurmaStr;
        response.sendRedirect(urlErro + urlMensagem);
    }
}