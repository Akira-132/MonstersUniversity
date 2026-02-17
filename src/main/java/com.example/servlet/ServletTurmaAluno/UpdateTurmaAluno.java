package com.example.servlet.ServletTurmaAluno;

import com.example.dao.TurmaAlunoDAO;
import com.example.models.TurmaAluno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/turmaaluno-update")
public class UpdateTurmaAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        TurmaAlunoDAO dao = new TurmaAlunoDAO();

        String idStr = request.getParameter("id");
        String fkAlunoIdStr = request.getParameter("fkAlunoId");
        String fkTurmaIdStr = request.getParameter("fkTurmaId");

        boolean success = false;
        String erro = null;

        try {
            int id = Integer.parseInt(idStr);
            int fkAlunoId = Integer.parseInt(fkAlunoIdStr);
            int fkTurmaId = Integer.parseInt(fkTurmaIdStr);

            TurmaAluno matricula = dao.readById(id);

            if (matricula != null) {
                matricula.setFkAlunoId(fkAlunoId);
                matricula.setFkTurmaId(fkTurmaId);

                if (dao.update(matricula) > 0) {
                    success = true;
                } else {
                    erro = "Erro ao atualizar matrícula.";
                }
            } else {
                erro = "Registro não encontrado.";
            }

        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate")) {
                erro = "Este aluno já está nesta turma.";
            } else {
                erro = e.getMessage();
            }
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/turmaaluno-read");
            return;
        }
        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "update");

        request.setAttribute("fkAlunoId_previo", fkAlunoIdStr);
        request.setAttribute("fkTurmaId_previo", fkTurmaIdStr);

        request.getRequestDispatcher("/turmaaluno-read").forward(request, response);
    }
}