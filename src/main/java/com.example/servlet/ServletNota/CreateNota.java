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

        NotaDAO notaDAO = new NotaDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = null;

        try {
            int semestre = Integer.parseInt(semestreStr);
            int ano = Integer.parseInt(anoStr);
            double valor = Double.parseDouble(notaValorStr.replace(",", "."));
            int fkAlunoId = Integer.parseInt(idAlunoStr);
            int fkDisciplinaId = Integer.parseInt(idDisciplinaStr);

            Nota novaNota = new Nota(tipo, semestre, ano, valor, fkAlunoId, fkDisciplinaId);

            if (notaDAO.create(novaNota)) {
                response.sendRedirect(request.getContextPath() + "/nota-read");
                return;
            } else {
                erro = "Erro ao lançar nota no banco.";
            }

        } catch (NumberFormatException e) {
            erro = "Verifique os números digitados (Ano, Semestre, Nota).";
        } catch (IllegalArgumentException e) {
            erro = "Validação: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado.";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "create");

        request.setAttribute("tipo_previo", tipo);
        request.setAttribute("semestre_previo", semestreStr);
        request.setAttribute("ano_previo", anoStr);
        request.setAttribute("nota_previo", notaValorStr);

        try {
            List<Nota> lista = notaDAO.read();
            for (Nota n : lista) {
                Aluno a = alunoDAO.readById(n.getFkAlunoId());
                if (a != null) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
                n.setAluno(a);
                n.setDisciplina(disciplinaDAO.readById(n.getFkDisciplinaId()));
            }
            request.setAttribute("listaNotas", lista);

            List<Aluno> listaAlunos = alunoDAO.read();
            for (Aluno a : listaAlunos) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
            request.setAttribute("listaAlunos", listaAlunos);

            List<Disciplina> listaDisciplinas = disciplinaDAO.read();
            request.setAttribute("listaDisciplinas", listaDisciplinas);

        } catch (Exception e) { e.printStackTrace(); }

        request.getRequestDispatcher("/WEB-INF/pages/notas.jsp").forward(request, response);
    }
}