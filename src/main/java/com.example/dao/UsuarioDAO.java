package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UsuarioDAO {

    public boolean create(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, sobrenome, email, senha, tipo) VALUES (?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSobrenome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setString(5, usuario.getTipo());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Usuario> read() throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, tipo FROM usuario ORDER BY id_usuario ASC";

        Conexao conexao = new Conexao();
        List<Usuario> listaUsuario = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Usuario usuario = new Usuario(
                        rset.getInt("id_usuario"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getString("tipo")
                );

                listaUsuario.add(usuario);
            }
        }

        return listaUsuario;
    }

    public Usuario readById(int id) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, tipo FROM usuario WHERE id_usuario = ?";

        Conexao conexao = new Conexao();
        Usuario usuario = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha"),
                            rset.getString("tipo")
                    );
                }
            }
        }

        return usuario;
    }

    public Usuario readByEmail(String email) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, tipo FROM usuario WHERE email = ?";

        Conexao conexao = new Conexao();
        Usuario usuario = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha"),
                            rset.getString("tipo")
                    );
                }
            }
        }

        return usuario;
    }

    public Usuario login(String email, String senha) throws SQLException {
        String sql = "SELECT id_usuario, nome, sobrenome, email, senha, tipo FROM usuario WHERE email = ? AND senha = ?";

        Conexao conexao = new Conexao();
        Usuario usuario = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha"),
                            rset.getString("tipo")
                    );
                }
            }
        }

        return usuario;
    }

    public int update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, sobrenome = ?, email = ?, senha = ?, tipo = ? WHERE id_usuario = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSobrenome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setString(5, usuario.getTipo());
            pstmt.setInt(6, usuario.getId());

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

    public int deleteByEmail(String email) throws SQLException {
        String sql = "DELETE FROM usuario WHERE email = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            return pstmt.executeUpdate();
        }
    }

}
