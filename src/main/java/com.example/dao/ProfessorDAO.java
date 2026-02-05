package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Professor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProfessorDAO {

    public boolean create(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor (usuario_id) VALUES (?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professor.getUsuarioId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Professor> read() throws SQLException {
        String sql = "SELECT * FROM professor ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Professor> listaProfessor = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Professor professor = new Professor(
                        rset.getInt("id"),
                        rset.getInt("usuario_id")
                );
                listaProfessor.add(professor);
            }
        }
        return listaProfessor;
    }

    public List<Professor> read(String nome, String orderBy, String direction) throws SQLException {
        Conexao conexao = new Conexao();
        List<Professor> listaProfessor = new LinkedList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM professor");
        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE usuario_id::text ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        String colunaOrdenacao = "id";
        if (orderBy != null) {
            if (orderBy.equalsIgnoreCase("usuario_id")) {
                colunaOrdenacao = "usuario_id";
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
                    listaProfessor.add(new Professor(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    ));
                }
            }
        }
        return listaProfessor;
    }

    public Professor read(int id) throws SQLException {
        String sql = "SELECT * FROM professor WHERE id = ?";
        Conexao conexao = new Conexao();
        Professor professor = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    professor = new Professor(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return professor;
    }

    public Professor read(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM professor WHERE usuario_id = ?";
        Conexao conexao = new Conexao();
        Professor professor = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(email));

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    professor = new Professor(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return professor;
    }

    public int update(Professor professor) throws SQLException {
        String sql = "UPDATE professor SET usuario_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professor.getUsuarioId());
            pstmt.setInt(2, professor.getId());

            return pstmt.executeUpdate();
        }
    }

    public int update(String nome, String email, String senha, int id) throws SQLException {
        String sql = "UPDATE professor SET usuario_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(nome));
            pstmt.setInt(2, id);

            return pstmt.executeUpdate();
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM professor WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int delete(String nome) throws SQLException {
        String sql = "DELETE FROM professor WHERE usuario_id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(nome));
            return pstmt.executeUpdate();
        }
    }
}
