package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Nota;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BoletimDAO {

    public boolean create(Nota nota) throws SQLException {
        String sql = "INSERT INTO nota (tipo, semestre, ano, nota, id_aluno, id_disciplina) VALUES (?, ?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nota.getTipo());
            pstmt.setInt(2, nota.getSemestre());
            pstmt.setInt(3, nota.getAno());
            pstmt.setDouble(4, nota.getNota());
            pstmt.setInt(5, nota.getFkAlunoId());
            pstmt.setInt(6, nota.getFkDisciplinaId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Nota> read() throws SQLException {
        String sql = "SELECT id_nota, tipo, semestre, ano, nota, id_aluno, id_disciplina FROM nota ORDER BY id_nota ASC";

        Conexao conexao = new Conexao();
        List<Nota> listaNota = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Nota nota = new Nota(
                        rset.getInt("id_nota"),
                        rset.getString("tipo"),
                        rset.getInt("semestre"),
                        rset.getDouble("nota"),
                        rset.getInt("id_aluno"),
                        rset.getInt("id_disciplina")
                );

                nota.setAno(rset.getInt("ano"));

                listaNota.add(nota);
            }
        }

        return listaNota;
    }

    public Nota readById(int id) throws SQLException {
        String sql = "SELECT id_nota, tipo, semestre, ano, nota, id_aluno, id_disciplina FROM nota WHERE id_nota = ?";

        Conexao conexao = new Conexao();
        Nota nota = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    nota = new Nota(
                            rset.getInt("id_nota"),
                            rset.getString("tipo"),
                            rset.getInt("semestre"),
                            rset.getDouble("nota"),
                            rset.getInt("id_aluno"),
                            rset.getInt("id_disciplina")
                    );

                    nota.setAno(rset.getInt("ano"));
                }
            }
        }

        return nota;
    }

    public List<Nota> readBySemestre(int semestre) throws SQLException {
        String sql = "SELECT id_nota, tipo, semestre, ano, nota, id_aluno, id_disciplina FROM nota WHERE semestre = ? ORDER BY id_nota ASC";

        Conexao conexao = new Conexao();
        List<Nota> listaNota = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, semestre);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {

                    Nota nota = new Nota(
                            rset.getInt("id_nota"),
                            rset.getString("tipo"),
                            rset.getInt("semestre"),
                            rset.getDouble("nota"),
                            rset.getInt("id_aluno"),
                            rset.getInt("id_disciplina")
                    );

                    nota.setAno(rset.getInt("ano"));

                    listaNota.add(nota);
                }
            }
        }

        return listaNota;
    }

    public int update(Nota nota) throws SQLException {
        String sql = "UPDATE nota SET tipo = ?, semestre = ?, ano = ?, nota = ?, id_aluno = ?, id_disciplina = ? WHERE id_nota = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nota.getTipo());
            pstmt.setInt(2, nota.getSemestre());
            pstmt.setInt(3, nota.getAno());
            pstmt.setDouble(4, nota.getNota());
            pstmt.setInt(5, nota.getFkAlunoId());
            pstmt.setInt(6, nota.getFkDisciplinaId());
            pstmt.setInt(7, nota.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM nota WHERE id_nota = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteBySemestre(int semestre) throws SQLException {
        String sql = "DELETE FROM nota WHERE semestre = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, semestre);
            return pstmt.executeUpdate();
        }
    }
}
