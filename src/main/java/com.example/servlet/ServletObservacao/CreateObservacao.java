package com.example.servlet.ServletObservacao;

import com.example.dao.ObservacaoDAO;
import com.example.dao.ProfessorDAO;
import com.example.models.Observacao;
import com.example.models.Professor;
import com.example.models.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/observacao-create")
public class CreateObservacao extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String texto = request.getParameter("texto");
        String idAlunoStr = request.getParameter("fkAlunoId");

        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        try {

            int fkAlunoId = Integer.parseInt(idAlunoStr);
            int fkProfessorId;

            // verifica se o usuário é professor
            Professor professor = professorDAO.readByUsuarioId(usuarioLogado.getId());

            if (professor != null) {
                // professor logado
                fkProfessorId = professor.getId();
            } else {
                response.sendRedirect(request.getContextPath() + "/turma-read");
                return;
            }

            Observacao novaObservacao = new Observacao(texto, fkProfessorId, fkAlunoId);

            boolean criada = observacaoDAO.create(novaObservacao);

            if (criada) {

                response.sendRedirect(
                        request.getContextPath() +
                                "/aluno-read?id=" + fkAlunoId +
                                "&sucesso=observacaoCriada"
                );

                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(
                request.getContextPath() +
                        "/aluno-read?id=" + idAlunoStr +
                        "&erro=observacao"
        );
    }
}