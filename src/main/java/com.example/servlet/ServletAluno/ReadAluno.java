package com.example.servlet.ServletAluno;

import java.io.IOException;
import java.util.List;
import com.example.models.Aluno;
import com.example.dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/aluno-read")
public class ReadAluno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AlunoDAO alunoDAO = new AlunoDAO();
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Aluno> lista = alunoDAO.read();
            request.setAttribute("listaAlunos", lista);

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Aluno alunoSelecionado = alunoDAO.readById(id);

                if (alunoSelecionado != null) {
                    request.setAttribute("alunoModal", alunoSelecionado);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar dados dos alunos.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}