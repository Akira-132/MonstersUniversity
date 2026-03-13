package com.example.servlet.ServletBoletim;

import com.example.dao.BoletimDAO;
import com.example.models.Boletim;
import com.example.models.Usuario;
import com.example.models.Aluno;
import com.example.dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/boletim-read")
public class ReadBoletim extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BoletimDAO boletimDAO = new BoletimDAO();
        int idAlunoParaBuscar = -1;

        try {
            HttpSession session = request.getSession(false);

            if (session != null && session.getAttribute("usuarioLogado") != null) {
                Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
                Aluno aluno = new AlunoDAO().readByUsuarioId(usuarioLogado.getId());

                if (aluno != null && aluno.getUsuario() != null) {
                    session.setAttribute("usuarioLogado", aluno.getUsuario());
                }

                idAlunoParaBuscar = (aluno != null) ? aluno.getId() : -1;
            }

            if (idAlunoParaBuscar != -1) {
                List<Boletim> listaBoletim = boletimDAO.readByAlunoId(idAlunoParaBuscar);
                request.setAttribute("listaBoletim", listaBoletim);
            } else {
                request.setAttribute("erro", "Não foi possível identificar o aluno para carregar o boletim.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado ao carregar o boletim.");
        }

        request.getRequestDispatcher("/WEB-INF/views/boletim.jsp").forward(request, response);
    }
}