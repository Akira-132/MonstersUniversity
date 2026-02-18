package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Turma;
import com.example.models.Disciplina;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TurmaDAO {

    public boolean create(Turma turma) throws SQLException {
        String sql = "INSERT INTO turma (periodo, sala, id_disciplina) VALUES (?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, turma.getPeriodo());
            pstmt.setString(2, turma.getSala());
            pstmt.setInt(3, turma.getFkDisciplinaId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Turma> read() throws SQLException {
        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina, d.nome, d.carga_horaria " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "ORDER BY t.id_turma ASC";

        Conexao conexao = new Conexao();
        List<Turma> listaTurma = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Disciplina disciplina = new Disciplina(
                        rset.getInt("id_disciplina"),
                        rset.getString("nome"),
                        rset.getInt("carga_horaria")
                );

                Turma turma = new Turma(
                        rset.getInt("id_turma"),
                        rset.getString("sala"),
                        rset.getString("periodo"),
                        rset.getInt("id_disciplina")
                );

                turma.setDisciplina(disciplina);

                listaTurma.add(turma);
            }
        }

        return listaTurma;
    }

    public Turma readById(int id) throws SQLException {
        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina, d.nome, d.carga_horaria " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "WHERE t.id_turma = ?";

        Conexao conexao = new Conexao();
        Turma turma = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Disciplina disciplina = new Disciplina(
                            rset.getInt("id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("carga_horaria")
                    );

                    turma = new Turma(
                            rset.getInt("id_turma"),
                            rset.getString("sala"),
                            rset.getString("periodo"),
                            rset.getInt("id_disciplina")
                    );

                    turma.setDisciplina(disciplina);
                }
            }
        }

        return turma;
    }

    public Turma readBySala(String sala) throws SQLException {
        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina, d.nome, d.carga_horaria " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "WHERE t.sala = ?";

        Conexao conexao = new Conexao();
        Turma turma = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sala);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Disciplina disciplina = new Disciplina(
                            rset.getInt("id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("carga_horaria")
                    );

                    turma = new Turma(
                            rset.getInt("id_turma"),
                            rset.getString("sala"),
                            rset.getString("periodo"),
                            rset.getInt("id_disciplina")
                    );

                    turma.setDisciplina(disciplina);
                }
            }
        }

        return turma;
    }

    public int update(Turma turma) throws SQLException {
        String sql = "UPDATE turma SET periodo = ?, sala = ?, id_disciplina = ? WHERE id_turma = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, turma.getPeriodo());
            pstmt.setString(2, turma.getSala());
            pstmt.setInt(3, turma.getFkDisciplinaId());
            pstmt.setInt(4, turma.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM turma WHERE id_turma = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteBySala(String sala) throws SQLException {
        String sql = "DELETE FROM turma WHERE sala = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sala);
            return pstmt.executeUpdate();
        }
    }
}
