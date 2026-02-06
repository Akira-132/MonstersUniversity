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
        String sql = "SELECT id, nome, professor_id FROM disciplina ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Disciplina> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                lista.add(new Disciplina(
                        rset.getInt("id"),
                        rset.getString("nome"),
                        rset.getInt("professor_id")
                ));
            }
        }
        return lista;
    }

    public Disciplina readById(int id) throws SQLException {
        String sql = "SELECT id, nome, professor_id FROM disciplina WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new Disciplina(
                            rset.getInt("id"),
                            rset.getString("nome"),
                            rset.getInt("professor_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Disciplina> readByNome(String nome, String orderBy, String direction) throws SQLException {
        Conexao conexao = new Conexao();
        List<Disciplina> lista = new LinkedList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT id, nome, professor_id FROM disciplina"
        );

        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sql.append(" WHERE nome ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        String colunaOrdenacao = "id";
        if ("nome".equalsIgnoreCase(orderBy)) {
            colunaOrdenacao = "nome";
        } else if ("professor_id".equalsIgnoreCase(orderBy)) {
            colunaOrdenacao = "professor_id";
        }

        String direcao = "ASC";
        if ("DESC".equalsIgnoreCase(direction)) {
            direcao = "DESC";
        }

        sql.append(" ORDER BY ").append(colunaOrdenacao).append(" ").append(direcao);

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    lista.add(new Disciplina(
                            rset.getInt("id"),
                            rset.getString("nome"),
                            rset.getInt("professor_id")
                    ));
                }
            }
        }
        return lista;
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

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByNome(String nome) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE nome = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            return pstmt.executeUpdate();
        }
    }
}
