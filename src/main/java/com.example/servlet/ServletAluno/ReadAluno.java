package com.example.servlet.ServletAluno;

import java.io.IOException;
import java.util.List;
import com.example.models.Aluno;
import com.example.models.Usuario;
import com.example.dao.AlunoDAO;
import com.example.dao.UsuarioDAO;
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
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Aluno> lista = alunoDAO.read();
            for (Aluno a : lista) {
                Usuario u = usuarioDAO.readById(a.getFkUsuarioId());
                a.setUsuario(u);
            }
            request.setAttribute("listaAlunos", lista);

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Aluno alunoSelecionado = alunoDAO.readById(id);

                if (alunoSelecionado != null) {
                    Usuario u = usuarioDAO.readById(alunoSelecionado.getFkUsuarioId());
                    alunoSelecionado.setUsuario(u);

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
            request.setAttribute("erro", "Erro ao carregar lista.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}