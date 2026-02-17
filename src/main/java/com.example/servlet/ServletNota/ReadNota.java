package com.example.servlet.ServletNota;

import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.NotaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Aluno;
import com.example.models.Disciplina;
import com.example.models.Nota;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/nota-read")
public class ReadNota extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NotaDAO notaDAO = new NotaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            request.setAttribute("listaNotas", notaDAO.read());
            request.setAttribute("listaAlunos", alunoDAO.read());
            request.setAttribute("listaDisciplinas", disciplinaDAO.read());

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (("prepararUpdate".equals(acao) || "prepararDelete".equals(acao)) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Nota n = notaDAO.readById(id);
                if (n != null) {
                    Aluno a = alunoDAO.readById(n.getFkAlunoId());
                    if (a != null) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                    n.setAluno(a);

                    n.setDisciplina(disciplinaDAO.readById(n.getFkDisciplinaId()));

                    request.setAttribute("notaModal", n);
                    request.setAttribute("modalAtivo", "prepararUpdate".equals(acao) ? "update" : "delete");
                }
            }
        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}