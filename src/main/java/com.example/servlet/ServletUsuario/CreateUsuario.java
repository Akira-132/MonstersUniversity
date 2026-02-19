//package com.example.servlet.ServletUsuario;
//
//import com.example.dao.UsuarioDAO;
//import com.example.models.Usuario;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet("/usuario-create")
//public class CreateUsuario extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        String nome = request.getParameter("nome");
//        String sobrenome = request.getParameter("sobrenome");
//        String email = request.getParameter("email");
//        String senha = request.getParameter("senha");
//        String tipo = request.getParameter("tipo");
//
//        UsuarioDAO dao = new UsuarioDAO();
//        boolean success = false;
//        String erro = null;
//
//        try {
//            Usuario novoUsuario = new Usuario(nome, sobrenome, email, senha, tipo);
//            int idGerado = dao.create(novoUsuario);
//
//            if (idGerado > 0) {
//                success = true;
//            } else {
//                erro = "Erro ao cadastrar usuário.";
//            }
//
//        } catch (IllegalArgumentException e) {
//            erro = "Erro de validação: " + e.getMessage();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            if (e.getMessage().contains("Duplicate entry") || e.getMessage().contains("UNIQUE")) {
//                erro = "Erro: E-mail já cadastrado.";
//            } else {
//                erro = "Erro de banco: " + e.getMessage();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            erro = "Erro inesperado: " + e.getMessage();
//        }
//
//        if (success) {
//            response.sendRedirect(request.getContextPath() + "/usuario-read");
//            return;
//        }
//
//        request.setAttribute("erro", erro);
//        request.setAttribute("nome_previo", nome);
//        request.setAttribute("sobrenome_previo", sobrenome);
//        request.setAttribute("email_previo", email);
//        request.setAttribute("tipo_previo", tipo);
//
//        List<Usuario> listaUsuarios = new ArrayList<>();
//        try {
//            listaUsuarios = dao.read();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        request.setAttribute("listaUsuarios", listaUsuarios);
//
//        request.setAttribute("modalAtivo", "create");
//        request.getRequestDispatcher("/WEB-INF/pages/usuarios.jsp").forward(request, response);
//    }
//}