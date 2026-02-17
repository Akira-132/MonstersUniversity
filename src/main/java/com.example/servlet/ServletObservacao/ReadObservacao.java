package com.example.servlet.ServletObservacao;

import java.io.IOException;
import java.util.List;

import com.example.models.Observacao;
import com.example.models.Professor;
import com.example.models.Aluno;
import com.example.models.Usuario;
import com.example.dao.ObservacaoDAO;
import com.example.dao.ProfessorDAO;
import com.example.dao.AlunoDAO;
import com.example.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/observacao-read")
public class ReadObservacao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");

        try {
            List<Observacao> lista = observacaoDAO.read();
            request.setAttribute("listaObservacoes", lista);

            request.setAttribute("listaProfessores", professorDAO.read());
            request.setAttribute("listaAlunos", alunoDAO.read());

            if ("prepararCreate".equals(acao)) {
                request.setAttribute("modalAtivo", "create");
            }
            else if (("prepararUpdate".equals(acao) || "prepararDelete".equals(acao)) && idStr != null) {
                int id = Integer.parseInt(idStr);
                Observacao obs = observacaoDAO.readById(id);

                if (obs != null) {
                    Professor p = professorDAO.readById(obs.getFkProfessorId());
                    if (p != null) {
                        Usuario uP = usuarioDAO.readById(p.getFkUsuarioId());
                        p.setUsuario(uP);
                        obs.setProfessor(p);
                    }

                    Aluno a = alunoDAO.readById(obs.getFkAlunoId());
                    if (a != null) {
                        Usuario uA = usuarioDAO.readById(a.getFkUsuarioId());
                        a.setUsuario(uA);
                        obs.setAluno(a);
                    }

                    request.setAttribute("observacaoModal", obs);
                    request.setAttribute("modalAtivo", "prepararUpdate".equals(acao) ? "update" : "delete");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao processar: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
    }
}