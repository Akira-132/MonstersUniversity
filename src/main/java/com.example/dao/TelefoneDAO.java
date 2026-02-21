package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Telefone;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TelefoneDAO {

    public boolean create(Telefone telefone) throws SQLException {
        String sql = "INSERT INTO telefone (telefone, id_usuario) VALUES (?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, telefone.getTelefone());
            pstmt.setInt(2, telefone.getFkUsuarioId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Telefone> read() throws SQLException {
        String sql = "SELECT t.id_telefone, t.telefone, t.id_usuario, " +
                "u.id_usuario, u.nome, u.sobrenome, u.email, u.senha" +
                "FROM telefone t " +
                "INNER JOIN usuario u ON t.id_usuario = u.id_usuario " +
                "ORDER BY t.id_telefone ASC";

        Conexao conexao = new Conexao();
        List<Telefone> listaTelefone = new LinkedList<>();

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

                Telefone telefone = new Telefone(
                        rset.getInt("id_telefone"),
                        rset.getString("telefone"),
                        rset.getInt("id_usuario")
                );

                telefone.setUsuario(usuario);

                listaTelefone.add(telefone);
            }
        }

        return listaTelefone;
    }

    public Telefone readById(int id) throws SQLException {
        String sql = "SELECT t.id_telefone, t.telefone, t.id_usuario, " +
                "u.id_usuario, u.nome, u.sobrenome, u.email, u.senha" +
                "FROM telefone t " +
                "INNER JOIN usuario u ON t.id_usuario = u.id_usuario " +
                "WHERE t.id_telefone = ?";

        Conexao conexao = new Conexao();
        Telefone telefone = null;

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

                    telefone = new Telefone(
                            rset.getInt("id_telefone"),
                            rset.getString("telefone"),
                            rset.getInt("id_usuario")
                    );

                    telefone.setUsuario(usuario);
                }
            }
        }

        return telefone;
    }

    public Telefone readByTelefone(String numero) throws SQLException {
        String sql = "SELECT t.id_telefone, t.telefone, t.id_usuario, " +
                "u.id_usuario, u.nome, u.sobrenome, u.email, u.senha" +
                "FROM telefone t " +
                "INNER JOIN usuario u ON t.id_usuario = u.id_usuario " +
                "WHERE t.telefone = ?";

        Conexao conexao = new Conexao();
        Telefone telefone = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numero);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );

                    telefone = new Telefone(
                            rset.getInt("id_telefone"),
                            rset.getString("telefone"),
                            rset.getInt("id_usuario")
                    );

                    telefone.setUsuario(usuario);
                }
            }
        }

        return telefone;
    }

    public int update(Telefone telefone) throws SQLException {
        String sql = "UPDATE telefone SET telefone = ?, id_usuario = ? WHERE id_telefone = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, telefone.getTelefone());
            pstmt.setInt(2, telefone.getFkUsuarioId());
            pstmt.setInt(3, telefone.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM telefone WHERE id_telefone = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByTelefone(String numero) throws SQLException {
        String sql = "DELETE FROM telefone WHERE telefone = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numero);
            return pstmt.executeUpdate();
        }
    }
}
