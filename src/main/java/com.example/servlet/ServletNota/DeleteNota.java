package com.example.servlet.ServletNota;

import com.example.dao.AlunoDAO;
import com.example.dao.DisciplinaDAO;
import com.example.dao.NotaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Aluno;
import com.example.models.Nota;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/nota-delete")
public class DeleteNota extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NotaDAO notaDAO = new NotaDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (notaDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/nota-read");
                return;
            } else {
                erro = "Não foi possível excluir a nota.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro ao excluir.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            AlunoDAO alunoDAO = new AlunoDAO();
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            List<Nota> lista = notaDAO.read();
            for (Nota n : lista) {
                Aluno a = alunoDAO.readById(n.getFkAlunoId());
                if (a != null) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                n.setAluno(a);
                n.setDisciplina(disciplinaDAO.readById(n.getFkDisciplinaId()));
            }
            request.setAttribute("listaNotas", lista);

            if (request.getParameter("id") != null) {
                request.setAttribute("notaModal", notaDAO.readById(Integer.parseInt(request.getParameter("id"))));
            }
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}