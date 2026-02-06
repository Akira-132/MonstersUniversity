package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Boletim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoletimDAO {

    public boolean create(Boletim boletim) throws SQLException {
        String sql = "INSERT INTO boletim (n1, n2, aluno_id, disciplina_id) VALUES (?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, boletim.getN1());
            pstmt.setDouble(2, boletim.getN2());
            pstmt.setInt(3, boletim.getAlunoId());
            pstmt.setInt(4, boletim.getDisciplinaId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Boletim> read() throws SQLException {
        String sql = "SELECT * FROM boletim ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Boletim> lista = new ArrayList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                lista.add(new Boletim(
                        rset.getInt("id"),
                        rset.getDouble("n1"),
                        rset.getDouble("n2"),
                        rset.getInt("aluno_id"),
                        rset.getInt("disciplina_id")
                ));
            }
        }
        return lista;
    }

    public Boletim readById(int id) throws SQLException {
        String sql = "SELECT * FROM boletim WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Boletim(
                            rset.getInt("id"),
                            rset.getDouble("n1"),
                            rset.getDouble("n2"),
                            rset.getInt("aluno_id"),
                            rset.getInt("disciplina_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Boletim> readByAlunoId(int alunoId) throws SQLException {
        String sql = "SELECT * FROM boletim WHERE aluno_id = ? ORDER BY disciplina_id";
        Conexao conexao = new Conexao();
        List<Boletim> lista = new ArrayList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    lista.add(new Boletim(
                            rset.getInt("id"),
                            rset.getDouble("n1"),
                            rset.getDouble("n2"),
                            rset.getInt("aluno_id"),
                            rset.getInt("disciplina_id")
                    ));
                }
            }
        }
        return lista;
    }

    public List<Boletim> readByDisciplinaId(int disciplinaId) throws SQLException {
        String sql = "SELECT * FROM boletim WHERE disciplina_id = ? ORDER BY aluno_id";
        Conexao conexao = new Conexao();
        List<Boletim> lista = new ArrayList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, disciplinaId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    lista.add(new Boletim(
                            rset.getInt("id"),
                            rset.getDouble("n1"),
                            rset.getDouble("n2"),
                            rset.getInt("aluno_id"),
                            rset.getInt("disciplina_id")
                    ));
                }
            }
        }
        return lista;
    }

    public int update(Boletim boletim) throws SQLException {
        String sql = "UPDATE boletim SET n1 = ?, n2 = ?, aluno_id = ?, disciplina_id = ? WHERE id = ? ";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, boletim.getN1());
            pstmt.setDouble(2, boletim.getN2());
            pstmt.setInt(3, boletim.getAlunoId());
            pstmt.setInt(4, boletim.getDisciplinaId());
            pstmt.setInt(5, boletim.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM boletim WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByAlunoId(int alunoId) throws SQLException {
        String sql = "DELETE FROM boletim WHERE aluno_id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            return pstmt.executeUpdate();
        }
    }
}
