package com.example.servlet.ServletAluno;

import com.example.dao.AlunoDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Aluno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/aluno-delete")
public class DeleteAluno extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AlunoDAO alunoDAO = new AlunoDAO();
        String erro = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (alunoDAO.deleteById(id) > 0) {
                response.sendRedirect(request.getContextPath() + "/aluno-read");
                return;
            } else {
                erro = "Não foi possível excluir.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro ao excluir (Pode haver registros vinculados).";
        }

        request.setAttribute("erro", erro);
        request.setAttribute("modalAtivo", "delete");

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Aluno> lista = alunoDAO.read();
            for (Aluno a : lista) a.setUsuario(usuarioDAO.readById(a.getFkUsuarioId()));
            request.setAttribute("listaAlunos", lista);
        } catch (Exception e) {}

        request.getRequestDispatcher("/WEB-INF/pages/alunos.jsp").forward(request, response);
    }
}