package com.example.servlet.ServletNota;

import java.io.IOException;
import java.util.List;
import com.example.models.Nota;
import com.example.models.Aluno;
import com.example.models.Disciplina;
import com.example.dao.NotaDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
            List<Nota> lista = notaDAO.read();
            for (Nota n : lista) {
                Aluno a = alunoDAO.readById(n.getFkAlunoId());
                if (a != null) {
                    a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                }
                n.setAluno(a);
                n.setDisciplina(disciplinaDAO.readById(n.getFkDisciplinaId()));
            }
            request.setAttribute("listaNotas", lista);

            if ("prepararCreate".equals(acao) || "prepararUpdate".equals(acao)) {
                List<Aluno> listaAlunos = alunoDAO.read();
                for (Aluno a : listaAlunos) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                request.setAttribute("listaAlunos", listaAlunos);

                List<Disciplina> listaDisciplinas = disciplinaDAO.read();
                request.setAttribute("listaDisciplinas", listaDisciplinas);
            }

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Nota nota = notaDAO.readById(id);

                if (nota != null) {
                    request.setAttribute("notaModal", nota);

                    if ("prepararUpdate".equals(acao)) {
                        request.setAttribute("modalAtivo", "update");
                    } else if ("prepararDelete".equals(acao)) {
                        request.setAttribute("modalAtivo", "delete");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar dados.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}