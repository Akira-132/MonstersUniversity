package com.example.servlet.ServletPerfil;

import com.example.dao.TelefoneDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Telefone;
import com.example.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@WebServlet("/perfil-update")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize       = 5 * 1024 * 1024,
        maxRequestSize    = 10 * 1024 * 1024
)
public class UpdatePerfil extends HttpServlet {

    private static final String PASTA_FOTOS = "assets/imgs/perfil";

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

        String email    = request.getParameter("email");
        String numero   = request.getParameter("telefone");
        String sobreMim = request.getParameter("sobreMim");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        String erro = null;

        try {

            if (email != null && !email.trim().isEmpty()) {
                Usuario emailExistente = usuarioDAO.readByEmail(email);
                if (emailExistente != null && emailExistente.getId() != usuarioLogado.getId()) {
                    request.setAttribute("erro", "Este e-mail já está em uso.");
                    request.getRequestDispatcher("/perfil-read").forward(request, response);
                    return;
                }
                usuarioLogado.setEmail(email);
            }

            if (sobreMim != null) {
                usuarioLogado.setSobreMim(sobreMim.trim().isEmpty() ? null : sobreMim.trim());
            }

            Part fotoPart = request.getPart("foto");
            if (fotoPart != null && fotoPart.getSize() > 0) {
                String nomeArquivo = salvarFoto(fotoPart, request);
                usuarioLogado.setFoto(nomeArquivo);
            }

            try {
                usuarioDAO.update(usuarioLogado);
                session.setAttribute("usuarioLogado", usuarioLogado);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erro", "Erro de banco de dados ao atualizar os dados.");
                request.getRequestDispatcher("/perfil-read").forward(request, response);
                return;
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

        } catch (IllegalArgumentException e) {
            erro = "Valor de campo inválido: " + e.getMessage();
            request.setAttribute("erro", erro);
            request.getRequestDispatcher("/perfil-read").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro de banco de dados ao atualizar os dados.");
            request.getRequestDispatcher("/perfil-read").forward(request, response);
        }
    }

    private String salvarFoto(Part part, HttpServletRequest request) throws IOException {
        String extensao = obterExtensao(part);
        String nomeArquivo = UUID.randomUUID().toString() + extensao;

        String pastaAbsoluta = request.getServletContext().getRealPath("") + File.separator + PASTA_FOTOS;
        Files.createDirectories(Paths.get(pastaAbsoluta));

        try (InputStream in = part.getInputStream()) {
            Files.copy(in, Paths.get(pastaAbsoluta, nomeArquivo), StandardCopyOption.REPLACE_EXISTING);
        }

        return nomeArquivo;
    }

    private String obterExtensao(Part part) {
        String header = part.getHeader("content-disposition");
        if (header != null) {
            for (String token : header.split(";")) {
                token = token.trim();
                if (token.startsWith("filename")) {
                    String nomeOriginal = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                    int ponto = nomeOriginal.lastIndexOf('.');
                    if (ponto >= 0) {
                        return nomeOriginal.substring(ponto).toLowerCase();
                    }
                }
            }
        }
        return ".jpg";
    }
}
