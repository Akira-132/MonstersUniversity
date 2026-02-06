package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Professor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProfessorDAO {

    public boolean create(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor (usuario_id) VALUES (?)";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professor.getUsuarioId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Professor> read() throws SQLException {
        String sql = "SELECT id, usuario_id FROM professor ORDER BY id ASC";

        Conexao conexao = new Conexao();
        List<Professor> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                lista.add(new Professor(
                        rset.getInt("id"),
                        rset.getInt("usuario_id")
                ));
            }
        }
        return lista;
    }

    public Professor readById(int id) throws SQLException {
        String sql = "SELECT id, usuario_id FROM professor WHERE id = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Professor(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return null;
    }

    public Professor readByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT id, usuario_id FROM professor WHERE usuario_id = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Professor(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return null;
    }

    public int update(Professor professor) throws SQLException {
        String sql = "UPDATE professor SET usuario_id = ? WHERE id = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professor.getUsuarioId());
            pstmt.setInt(2, professor.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM professor WHERE id = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }
}
