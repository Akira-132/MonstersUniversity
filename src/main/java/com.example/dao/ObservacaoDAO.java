package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Observacao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ObservacaoDAO {

    public boolean create(Observacao observacao) throws SQLException {
        String sql = "INSERT INTO observacao (texto, data_envio, professor_id, aluno_id) VALUES (?,?,?,?)";
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
        String sql = "SELECT * FROM observacao ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Observacao> listaObservacao = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Observacao observacao = new Observacao(
                        rset.getInt("id"),
                        rset.getString("texto"),
                        rset.getTimestamp("data_envio").toLocalDateTime(),
                        rset.getInt("professor_id"),
                        rset.getInt("aluno_id")
                );
                listaObservacao.add(observacao);
            }
        }
        return listaObservacao;
    }

    public List<Observacao> read(String nome, String orderBy, String direction) throws SQLException {
        Conexao conexao = new Conexao();
        List<Observacao> listaObservacao = new LinkedList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM observacao");
        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE aluno_id = ?");
            parametros.add(Integer.parseInt(nome));
        }

        String colunaOrdenacao = "id";
        if (orderBy != null) {
            if (orderBy.equalsIgnoreCase("data_envio")) {
                colunaOrdenacao = "data_envio";
            } else if (orderBy.equalsIgnoreCase("professor_id")) {
                colunaOrdenacao = "professor_id";
            }
        }

        String dir = "ASC";
        if (direction != null && direction.equalsIgnoreCase("DESC")) {
            dir = "DESC";
        }

        sqlBuilder.append(" ORDER BY ").append(colunaOrdenacao).append(" ").append(dir);

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    listaObservacao.add(new Observacao(
                            rset.getInt("id"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("professor_id"),
                            rset.getInt("aluno_id")
                    ));
                }
            }
        }
        return listaObservacao;
    }

    public Observacao read(int id) throws SQLException {
        String sql = "SELECT * FROM observacao WHERE id = ?";
        Conexao conexao = new Conexao();
        Observacao observacao = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    observacao = new Observacao(
                            rset.getInt("id"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("professor_id"),
                            rset.getInt("aluno_id")
                    );
                }
            }
        }
        return observacao;
    }

    public Observacao read(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM observacao WHERE texto = ?";
        Conexao conexao = new Conexao();
        Observacao observacao = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    observacao = new Observacao(
                            rset.getInt("id"),
                            rset.getString("texto"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("professor_id"),
                            rset.getInt("aluno_id")
                    );
                }
            }
        }
        return observacao;
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

    public int update(String nome, String email, String senha, int id) throws SQLException {
        String sql = "UPDATE observacao SET texto = ?, professor_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setInt(2, Integer.parseInt(email));
            pstmt.setInt(3, id);

            return pstmt.executeUpdate();
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM observacao WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int delete(String nome) throws SQLException {
        String sql = "DELETE FROM observacao WHERE texto = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            return pstmt.executeUpdate();
        }
    }
}
