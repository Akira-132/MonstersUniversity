package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Aluno;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AlunoDAO {

    public boolean create(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (matricula, usuario_id) VALUES (?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getMatricula());
            pstmt.setInt(2, aluno.getUsuarioId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Aluno> read() throws SQLException {
        String sql = "SELECT * FROM aluno ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Aluno> listaAluno = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Aluno aluno = new Aluno(
                        rset.getInt("id"),
                        rset.getString("matricula"),
                        rset.getInt("usuario_id")
                );
                listaAluno.add(aluno);
            }
        }
        return listaAluno;
    }

    public Aluno readById(int id) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        Conexao conexao = new Conexao();
        Aluno aluno = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    aluno = new Aluno(
                            rset.getInt("id"),
                            rset.getString("matricula"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return aluno;
    }

    public Aluno readByMatricula(String matricula) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();
        Aluno aluno = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricula);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    aluno = new Aluno(
                            rset.getInt("id"),
                            rset.getString("matricula"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return aluno;
    }

    public int update(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET matricula = ?, usuario_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getMatricula());
            pstmt.setInt(2, aluno.getUsuarioId());
            pstmt.setInt(3, aluno.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM aluno WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByMatricula(String matricula) throws SQLException {
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricula);
            return pstmt.executeUpdate();
        }
    }
}
