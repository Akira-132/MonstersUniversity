package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Professor;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProfessorDAO {

    public boolean create(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor (id_usuario) VALUES (?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professor.getFkUsuarioId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Professor> read() throws SQLException {
        String sql = "SELECT " +
                "p.id_professor, " +
                "p.id_usuario AS p_id_usuario, " +
                "u.id_usuario AS u_id_usuario, " +
                "u.nome, u.sobrenome, u.email, u.senha, u.tipo " +
                "FROM professor p " +
                "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "ORDER BY p.id_professor ASC";

        Conexao conexao = new Conexao();
        List<Professor> listaProfessor = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Usuario usuario = new Usuario(
                        rset.getInt("u_id_usuario"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getString("tipo")
                );

                Professor professor = new Professor(
                        rset.getInt("id_professor"),
                        rset.getInt("p_id_usuario")
                );

                professor.setUsuario(usuario);
                listaProfessor.add(professor);
            }
        }

        return listaProfessor;
    }

    public Professor readById(int id) throws SQLException {
        String sql = "SELECT " +
                "p.id_professor, " +
                "p.id_usuario AS p_id_usuario, " +
                "u.id_usuario AS u_id_usuario, " +
                "u.nome, u.sobrenome, u.email, u.senha, u.tipo " +
                "FROM professor p " +
                "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "WHERE p.id_professor = ?";

        Conexao conexao = new Conexao();
        Professor professor = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Usuario usuario = new Usuario(
                            rset.getInt("u_id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha"),
                            rset.getString("tipo")
                    );

                    professor = new Professor(
                            rset.getInt("id_professor"),
                            rset.getInt("p_id_usuario")
                    );

                    professor.setUsuario(usuario);
                }
            }
        }

        return professor;
    }

    public Professor readByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT " +
                "p.id_professor, " +
                "p.id_usuario AS p_id_usuario, " +
                "u.id_usuario AS u_id_usuario, " +
                "u.nome, u.sobrenome, u.email, u.senha, u.tipo " +
                "FROM professor p " +
                "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "WHERE p.id_usuario = ?";

        Conexao conexao = new Conexao();
        Professor professor = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Usuario usuario = new Usuario(
                            rset.getInt("u_id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha"),
                            rset.getString("tipo")
                    );

                    professor = new Professor(
                            rset.getInt("id_professor"),
                            rset.getInt("p_id_usuario")
                    );

                    professor.setUsuario(usuario);
                }
            }
        }

        return professor;
    }

    public int update(Professor professor) throws SQLException {
        String sql = "UPDATE professor SET id_usuario = ? WHERE id_professor = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professor.getFkUsuarioId());
            pstmt.setInt(2, professor.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM professor WHERE id_professor = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByUsuarioId(int usuarioId) throws SQLException {
        String sql = "DELETE FROM professor WHERE id_usuario = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            return pstmt.executeUpdate();
        }
    }
}
