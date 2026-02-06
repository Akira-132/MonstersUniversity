package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Observacao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ObservacaoDAO {

    public boolean create(Observacao observacao) throws SQLException {
        String sql = "INSERT INTO observacao (texto, data_envio, professor_id, aluno_id) VALUES (?, ?, ?, ?)";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, observacao.getTexto());
            pstmt.setTimestamp(2, Timestamp.valueOf(observacao.getDataEnvio()));
            pstmt.setInt(3, observacao.getProfessorId());
            pstmt.setInt(4, observacao.getAlunoId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Observacao> read() throws SQLException {
        String sql = "SELECT id, texto, data_envio, professor_id, aluno_id FROM observacao ORDER BY id ASC ";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                lista.add(new Observacao(
                        rset.getInt("id"),
                        rset.getString("texto"),
                        rset.getTimestamp("data_envio").toLocalDateTime(),
                        rset.getInt("professor_id"),
                        rset.getInt("aluno_id")
                ));
            }
        }
        return lista;
    }

    public Observacao readById(int id) throws SQLException {
        String sql = "SELECT id, texto, data_envio, professor_id, aluno_id FROM observacao WHERE id = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Observacao(
                            rset.getInt("id"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("professor_id"),
                            rset.getInt("aluno_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Observacao> readByAlunoId(int alunoId) throws SQLException {
        String sql = "SELECT id, texto, data_envio, professor_id, aluno_id FROM observacao WHERE aluno_id = ? ORDER BY data_envio DESC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    lista.add(new Observacao(
                            rset.getInt("id"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("professor_id"),
                            rset.getInt("aluno_id")
                    ));
                }
            }
        }
        return lista;
    }

    public List<Observacao> readByProfessorId(int professorId) throws SQLException {
        String sql = "SELECT id, texto, data_envio, professor_id, aluno_id FROM observacao WHERE professor_id = ? ORDER BY data_envio DESC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    lista.add(new Observacao(
                            rset.getInt("id"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("professor_id"),
                            rset.getInt("aluno_id")
                    ));
                }
            }
        }
        return lista;
    }

    public int update(Observacao observacao) throws SQLException {
        String sql = "UPDATE observacao SET texto = ?, data_envio = ?, professor_id = ?, aluno_id = ? WHERE id = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, observacao.getTexto());
            pstmt.setTimestamp(2, Timestamp.valueOf(observacao.getDataEnvio()));
            pstmt.setInt(3, observacao.getProfessorId());
            pstmt.setInt(4, observacao.getAlunoId());
            pstmt.setInt(5, observacao.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM observacao WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }
}
