package com.example.servlet.ServletPerfil;

import com.example.dao.TelefoneDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Telefone;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/perfil-update")
public class UpdatePerfil extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login");
            return;
        }

        String email = request.getParameter("email");
        String numero = request.getParameter("telefone");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        String erro = null;

        try {
            if (email != null && !email.trim().isEmpty()) {
                Usuario emailExistente = usuarioDAO.readByEmail(email);
                if (emailExistente != null && emailExistente.getId() != usuarioLogado.getId()) {
                    request.setAttribute("erro", "Este e-mail já está em uso.");
                    request.getRequestDispatcher("/WEB-INF/views/perfil-aluno.jsp").forward(request, response);
                    return;
                }

                usuarioLogado.setEmail(email);
                usuarioDAO.update(usuarioLogado);

                session.setAttribute("usuarioLogado", usuarioLogado);
            }

            if (numero != null && !numero.trim().isEmpty()) {
                List<Telefone> telefones = telefoneDAO.readByUsuarioId(usuarioLogado.getId());
                if (telefones != null && !telefones.isEmpty()) {
                    Telefone telAtual = telefones.get(0);
                    telAtual.setTelefone(numero);
                    telefoneDAO.update(telAtual);
                } else {
                    Telefone novoTel = new Telefone(numero, usuarioLogado.getId());
                    telefoneDAO.create(novoTel);
                }
            }

            response.sendRedirect(request.getContextPath() + "/perfil-read?sucesso=ok");
            return;

        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            erro = "Telefone e/ou email inválidos. Verifique se foram digitados corretamente";
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro de banco de dados ao atualizar os dados.";
        }

        request.setAttribute("erro", erro);
        request.getRequestDispatcher("/perfil-read").forward(request, response);
    }
}