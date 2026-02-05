package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Boletim;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BoletimDAO {

    public boolean create(Boletim boletim) throws SQLException {
        String sql = "INSERT INTO boletim (n1, n2, aluno_id, disciplina_id) VALUES (?,?,?,?)";
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
        List<Boletim> listaBoletim = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Boletim boletim = new Boletim(
                        rset.getInt("id"),
                        rset.getDouble("n1"),
                        rset.getDouble("n2"),
                        rset.getInt("aluno_id"),
                        rset.getInt("disciplina_id")
                );
                listaBoletim.add(boletim);
            }
        }
        return listaBoletim;
    }

    public List<Boletim> read(String nome, String orderBy, String direction) throws SQLException {
        Conexao conexao = new Conexao();
        List<Boletim> listaBoletim = new LinkedList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM boletim");
        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE aluno_id = ?");
            parametros.add(Integer.parseInt(nome));
        }

        String colunaOrdenacao = "id";
        if (orderBy != null) {
            if (orderBy.equalsIgnoreCase("aluno_id")) {
                colunaOrdenacao = "aluno_id";
            } else if (orderBy.equalsIgnoreCase("disciplina_id")) {
                colunaOrdenacao = "disciplina_id";
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
                    listaBoletim.add(new Boletim(
                            rset.getInt("id"),
                            rset.getDouble("n1"),
                            rset.getDouble("n2"),
                            rset.getInt("aluno_id"),
                            rset.getInt("disciplina_id")
                    ));
                }
            }
        }
        return listaBoletim;
    }

    public Boletim read(int id) throws SQLException {
        String sql = "SELECT * FROM boletim WHERE id = ?";
        Conexao conexao = new Conexao();
        Boletim boletim = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    boletim = new Boletim(
                            rset.getInt("id"),
                            rset.getDouble("n1"),
                            rset.getDouble("n2"),
                            rset.getInt("aluno_id"),
                            rset.getInt("disciplina_id")
                    );
                }
            }
        }
        return boletim;
    }

    public Boletim read(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM boletim WHERE aluno_id = ?";
        Conexao conexao = new Conexao();
        Boletim boletim = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(email));

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    boletim = new Boletim(
                            rset.getInt("id"),
                            rset.getDouble("n1"),
                            rset.getDouble("n2"),
                            rset.getInt("aluno_id"),
                            rset.getInt("disciplina_id")
                    );
                }
            }
        }
        return boletim;
    }

    public int update(Boletim boletim) throws SQLException {
        String sql = "UPDATE boletim SET n1 = ?, n2 = ?, aluno_id = ?, disciplina_id = ? WHERE id = ?";
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

    public int update(String nome, String email, String senha, int id) throws SQLException {
        String sql = "UPDATE boletim SET n1 = ?, n2 = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, Double.parseDouble(nome));
            pstmt.setDouble(2, Double.parseDouble(email));
            pstmt.setInt(3, id);

            return pstmt.executeUpdate();
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM boletim WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int delete(String nome) throws SQLException {
        String sql = "DELETE FROM boletim WHERE aluno_id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(nome));
            return pstmt.executeUpdate();
        }
    }
}
