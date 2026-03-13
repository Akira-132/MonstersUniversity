package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UsuarioDAO {

    public boolean create(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, sobrenome, email, senha, foto, sobre_mim) VALUES (?, ?, ?, ?, ?, ?)";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSobrenome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setString(5, usuario.getFoto());
            pstmt.setString(6, usuario.getSobreMim());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Usuario> read() throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, foto, sobre_mim FROM usuario ORDER BY nome ASC";

        Conexao conexao = new Conexao();
        List<Usuario> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Usuario usuario = new Usuario(
                        rset.getInt("id_usuario"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getString("email"),
                        rset.getString("senha")
                );
                usuario.setFoto(rset.getString("foto"));
                usuario.setSobreMim(rset.getString("sobre_mim"));
                lista.add(usuario);
            }
        }
        return lista;
    }

    public Usuario readById(int id) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, foto, sobre_mim FROM usuario WHERE id_usuario = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );
                    usuario.setFoto(rset.getString("foto"));
                    usuario.setSobreMim(rset.getString("sobre_mim"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public Usuario readByEmail(String email) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, foto, sobre_mim FROM usuario WHERE email = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );
                    usuario.setFoto(rset.getString("foto"));
                    usuario.setSobreMim(rset.getString("sobre_mim"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public Usuario login(String email, String senha) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, foto, sobre_mim FROM usuario WHERE email = ? AND senha = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );
                    usuario.setFoto(rset.getString("foto"));
                    usuario.setSobreMim(rset.getString("sobre_mim"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public int update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, sobrenome = ?, email = ?, senha = ?, foto = ?, sobre_mim = ? WHERE id_usuario = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSobrenome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setString(5, usuario.getFoto());
            pstmt.setString(6, usuario.getSobreMim());
            pstmt.setInt(7, usuario.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }
}