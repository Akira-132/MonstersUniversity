package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Aluno;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AlunoDAO {

    public boolean create(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (matricula, usuario_id) VALUES (?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getMatricula());
            pstmt.setInt(2, aluno.getUsuarioId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Aluno> read() throws SQLException {
        String sql = "SELECT * FROM aluno ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Aluno> listaAluno = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Aluno aluno = new Aluno(
                        rset.getInt("id"),
                        rset.getString("matricula"),
                        rset.getInt("usuario_id")
                );
                listaAluno.add(aluno);
            }
        }
        return listaAluno;
    }

    public List<Aluno> read(String nome, String orderBy, String direction) throws SQLException {
        Conexao conexao = new Conexao();
        List<Aluno> listaAluno = new LinkedList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM aluno");
        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE matricula ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        String colunaOrdenacao = "id";
        if (orderBy != null) {
            if (orderBy.equalsIgnoreCase("matricula")) {
                colunaOrdenacao = "matricula";
            } else if (orderBy.equalsIgnoreCase("usuario_id")) {
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
                    listaAluno.add(new Aluno(
                            rset.getInt("id"),
                            rset.getString("matricula"),
                            rset.getInt("usuario_id")
                    ));
                }
            }
        }
        return listaAluno;
    }

    public Aluno read(int id) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        Conexao conexao = new Conexao();
        Aluno aluno = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    aluno = new Aluno(
                            rset.getInt("id"),
                            rset.getString("matricula"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return aluno;
    }

    public Aluno read(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();
        Aluno aluno = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    aluno = new Aluno(
                            rset.getInt("id"),
                            rset.getString("matricula"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return aluno;
    }

    public int update(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET matricula = ?, usuario_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getMatricula());
            pstmt.setInt(2, aluno.getUsuarioId());
            pstmt.setInt(3, aluno.getId());

            return pstmt.executeUpdate();
        }
    }

    public int update(String nome, String email, String senha, int id) throws SQLException {
        String sql = "UPDATE aluno SET matricula = ?, usuario_id = ? WHERE id = ?";
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
        String sql = "DELETE FROM aluno WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int delete(String nome) throws SQLException {
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            return pstmt.executeUpdate();
        }
    }
}
