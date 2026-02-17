package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Observacao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ObservacaoDAO {

    public boolean create(Observacao observacao) throws SQLException {
        String sql = "INSERT INTO observacao (texto, data_envio, id_professor, id_aluno) VALUES (?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, observacao.getTexto());
            pstmt.setTimestamp(2, Timestamp.valueOf(observacao.getDataEnvio()));
            pstmt.setInt(3, observacao.getFkProfessorId());
            pstmt.setInt(4, observacao.getFkAlunoId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Observacao> read() throws SQLException {
        String sql = "SELECT id_observacao, texto, data_envio, id_professor, id_aluno FROM observacao ORDER BY id_observacao ASC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Observacao observacao = new Observacao(
                        rset.getInt("id_observacao"),
                        rset.getString("texto"),
                        rset.getTimestamp("data_envio").toLocalDateTime(),
                        rset.getInt("id_professor"),
                        rset.getInt("id_aluno")
                );

                lista.add(observacao);
            }
        }

        return lista;
    }

    public Observacao readById(int id) throws SQLException {
        String sql = "SELECT id_observacao, texto, data_envio, id_professor, id_aluno FROM observacao WHERE id_observacao = ?";

        Conexao conexao = new Conexao();
        Observacao observacao = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    observacao = new Observacao(
                            rset.getInt("id_observacao"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("id_professor"),
                            rset.getInt("id_aluno")
                    );
                }
            }
        }

        return observacao;
    }

    public List<Observacao> readByAlunoId(int alunoId) throws SQLException {
        String sql = "SELECT id_observacao, texto, data_envio, id_professor, id_aluno FROM observacao WHERE id_aluno = ? ORDER BY id_observacao ASC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {

                    Observacao observacao = new Observacao(
                            rset.getInt("id_observacao"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("id_professor"),
                            rset.getInt("id_aluno")
                    );

                    lista.add(observacao);
                }
            }
        }

        return lista;
    }

    public List<Observacao> readByProfessorId(int professorId) throws SQLException {
        String sql = "SELECT id_observacao, texto, data_envio, id_professor, id_aluno FROM observacao WHERE id_professor = ? ORDER BY id_observacao ASC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {

                    Observacao observacao = new Observacao(
                            rset.getInt("id_observacao"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("id_professor"),
                            rset.getInt("id_aluno")
                    );

                    lista.add(observacao);
                }
            }
        }

        return lista;
    }

    public int update(Observacao observacao) throws SQLException {
        String sql = "UPDATE observacao SET texto = ?, data_envio = ?, id_professor = ?, id_aluno = ? WHERE id_observacao = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, observacao.getTexto());
            pstmt.setTimestamp(2, Timestamp.valueOf(observacao.getDataEnvio()));
            pstmt.setInt(3, observacao.getFkProfessorId());
            pstmt.setInt(4, observacao.getFkAlunoId());
            pstmt.setInt(5, observacao.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM observacao WHERE id_observacao = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByAlunoId(int alunoId) throws SQLException {
        String sql = "DELETE FROM observacao WHERE id_aluno = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByProfessorId(int professorId) throws SQLException {
        String sql = "DELETE FROM observacao WHERE id_professor = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professorId);
            return pstmt.executeUpdate();
        }
    }
}
