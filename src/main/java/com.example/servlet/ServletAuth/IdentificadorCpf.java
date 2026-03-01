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
        if (cpf != null) {
            cpf = cpf.replaceAll("[^\\d]", "");
        }

        System.out.println("[IdentificadorCpf] recebido cpf = '" + cpf + "'");

        AlunoDAO alunoDAO = new AlunoDAO();

        try {
            Aluno aluno = alunoDAO.readByCpf(cpf);

            if (aluno != null) {
                System.out.println("[IdentificadorCpf] aluno encontrado: id=" + aluno.getId() + " cpf=" + aluno.getCpf());

                request.setAttribute("aluno", aluno);
                request.getRequestDispatcher("/WEB-INF/views/matricula.jsp").forward(request, response);
                return;
            } else {
                System.out.println("[IdentificadorCpf] nenhum aluno encontrado para cpf=" + cpf);
                request.setAttribute("erro", "CPF não encontrado no sistema.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao verificar o CPF.");
        }

        request.getRequestDispatcher("/WEB-INF/views/verificaçaoUsuario.jsp").forward(request, response);
    }
}