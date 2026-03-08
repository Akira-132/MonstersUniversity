package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UsuarioDAO {

    public boolean create(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, sobrenome, email, senha) VALUES (?, ?, ?, ?)";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSobrenome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Usuario> read() throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha FROM usuario ORDER BY nome ASC";

        Conexao conexao = new Conexao();
        List<Usuario> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                lista.add(new Usuario(
                        rset.getInt("id_usuario"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getString("email"),
                        rset.getString("senha")
                ));
            }
        }
        return lista;
    }

    public Usuario readById(int id) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha FROM usuario WHERE id_usuario = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );
                }
            }
        }
        return null;
    }

    public Usuario readByEmail(String email) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha FROM usuario WHERE email = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );
                }
            }
        }
        return null;
    }

    public Usuario login(String email, String senha) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha FROM usuario WHERE email = ? AND senha = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );
                }
            }
        }
        return null;
    }

    public int update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, sobrenome = ?, email = ?, senha = ? WHERE id_usuario = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSobrenome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setInt(5, usuario.getId());

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
