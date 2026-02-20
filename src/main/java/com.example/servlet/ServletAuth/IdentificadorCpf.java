package com.example.servlet.ServletAuth;

import com.example.dao.AlunoDAO;
import com.example.models.Aluno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/verificar-cpf")
public class IdentificadorCpf extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String cpf = request.getParameter("cpf");

        AlunoDAO alunoDAO = new AlunoDAO();

        try {
            if (cpf != null) {
                cpf = cpf.replaceAll("[^\\d]", "");
            }

            Aluno aluno = alunoDAO.readByCpf(cpf);

            if (aluno != null) {
                request.setAttribute("alunoAtivacao", aluno);
                request.getRequestDispatcher("matricula.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("erro", "CPF não encontrado no sistema.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao verificar o CPF.");
        }

        request.getRequestDispatcher("verificacao-aluno.jsp").forward(request, response);
    }
}