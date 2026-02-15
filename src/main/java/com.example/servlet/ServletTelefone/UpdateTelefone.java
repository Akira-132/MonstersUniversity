package com.example.servlet.ServletTelefone;

import com.example.dao.TelefoneDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Telefone;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/telefone-update")
public class UpdateTelefone extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TelefoneDAO dao = new TelefoneDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Telefone> listaTelefones = new ArrayList<>();
        String erro = null;
        String idParam = request.getParameter("id");

        try {
            listaTelefones = dao.read();
            request.setAttribute("listaUsuarios", usuarioDAO.read());

            Telefone telefoneModal = null;
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                telefoneModal = dao.readById(id);

                if (telefoneModal != null) {
                    request.setAttribute("telefoneModal", telefoneModal);
                    request.setAttribute("abrirModal", "update");
                } else {
                    erro = "Telefone ID " + id + " não encontrado.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco: " + e.getMessage();
        } catch (NumberFormatException e) {
            erro = "ID inválido: " + idParam;
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (erro != null) request.setAttribute("erro", erro);
        request.setAttribute("listaTelefones", listaTelefones);

        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        TelefoneDAO dao = new TelefoneDAO();
        int id = 0;
        boolean success = false;
        String erro = null;

        String idParam = request.getParameter("id");
        String telefoneStr = request.getParameter("telefone");
        String idUsuarioStr = request.getParameter("idUsuario");

        try {
            id = Integer.parseInt(idParam);
            int idUsuario = Integer.parseInt(idUsuarioStr);

            Telefone telefoneObj = dao.readById(id);
            if (telefoneObj == null) {
                throw new Exception("Telefone ID " + id + " não encontrado.");
            }

            telefoneObj.setTelefone(telefoneStr);
            telefoneObj.setFkUsuarioId(idUsuario);

            int resultado = dao.update(telefoneObj);

            if (resultado > 0) {
                success = true;
            } else {
                erro = "Não foi possível atualizar (ID: " + id + ").";
            }

        } catch (IllegalArgumentException | NullPointerException e) {
            erro = "Erro de validação: " + e.getMessage();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("violates foreign key constraint")) {
                erro = "Erro: Usuário inválido.";
            } else {
                erro = "Erro de banco ao atualizar: " + e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado: " + e.getMessage();
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/telefones-crud");
            return;
        }

        request.setAttribute("erro", erro);
        request.setAttribute("telefone_previo", telefoneStr);
        request.setAttribute("idUsuario_previo", idUsuarioStr);

        List<Telefone> listaTelefones = new ArrayList<>();
        try {
            listaTelefones = dao.read();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            request.setAttribute("listaUsuarios", usuarioDAO.read());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listaTelefones", listaTelefones);

        if (id > 0) {
            try { request.setAttribute("telefoneModal", dao.readById(id)); } catch(Exception e){}
        }

        request.setAttribute("abrirModal", "update");
        request.getRequestDispatcher("/WEB-INF/pages/telefones.jsp").forward(request, response);
    }
}