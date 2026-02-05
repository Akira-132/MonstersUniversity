package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Disciplina;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DisciplinaDAO {

    public boolean create(Disciplina disciplina) throws SQLException {
        String sql = "INSERT INTO disciplina (nome, professor_id) VALUES (?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, disciplina.getNome());
            pstmt.setInt(2, disciplina.getProfessorId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Disciplina> read() throws SQLException {
        String sql = "SELECT * FROM disciplina ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Disciplina> listaDisciplina = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Disciplina disciplina = new Disciplina(
                        rset.getInt("id"),
                        rset.getString("nome"),
                        rset.getInt("professor_id")
                );
                listaDisciplina.add(disciplina);
            }
        }
        return listaDisciplina;
    }

    public List<Disciplina> read(String nome, String orderBy, String direction) throws SQLException {
        Conexao conexao = new Conexao();
        List<Disciplina> listaDisciplina = new LinkedList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM disciplina");
        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE nome ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        String colunaOrdenacao = "id";
        if (orderBy != null) {
            if (orderBy.equalsIgnoreCase("nome")) {
                colunaOrdenacao = "nome";
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
                    listaDisciplina.add(new Disciplina(
                            rset.getInt("id"),
                            rset.getString("nome"),
                            rset.getInt("professor_id")
                    ));
                }
            }
        }
        return listaDisciplina;
    }

    public Disciplina read(int id) throws SQLException {
        String sql = "SELECT * FROM disciplina WHERE id = ?";
        Conexao conexao = new Conexao();
        Disciplina disciplina = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    disciplina = new Disciplina(
                            rset.getInt("id"),
                            rset.getString("nome"),
                            rset.getInt("professor_id")
                    );
                }
            }
        }
        return disciplina;
    }

    public Disciplina read(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM disciplina WHERE nome = ?";
        Conexao conexao = new Conexao();
        Disciplina disciplina = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    disciplina = new Disciplina(
                            rset.getInt("id"),
                            rset.getString("nome"),
                            rset.getInt("professor_id")
                    );
                }
            }
        }
        return disciplina;
    }

    public int update(Disciplina disciplina) throws SQLException {
        String sql = "UPDATE disciplina SET nome = ?, professor_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, disciplina.getNome());
            pstmt.setInt(2, disciplina.getProfessorId());
            pstmt.setInt(3, disciplina.getId());

            return pstmt.executeUpdate();
        }
    }

    public int update(String nome, String email, String senha, int id) throws SQLException {
        String sql = "UPDATE disciplina SET nome = ?, professor_id = ? WHERE id = ?";
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
        String sql = "DELETE FROM disciplina WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int delete(String nome) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE nome = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            return pstmt.executeUpdate();
        }
    }
}
