package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Admin;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class AdminDAO {

    public boolean create(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (usuario_id) VALUES (?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, admin.getUsuarioId());
            return pstmt.executeUpdate() > 0;
        }
    }


    public List<Admin> read() throws SQLException {
        String sql = "SELECT * FROM admin ORDER BY id ASC";
        Conexao conexao = new Conexao();
        List<Admin> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                Admin admin = new Admin(
                        rset.getInt("id"),
                        rset.getInt("usuario_id")
                );
                lista.add(admin);
            }
        }
        return lista;
    }

    public List<Admin> read(String nome, String orderBy, String direction) throws SQLException {
        Conexao conexao = new Conexao();
        List<Admin> lista = new LinkedList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM admin");
        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE CAST(usuario_id AS TEXT) ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        String colunaOrdenacao = "id";
        if (orderBy != null && orderBy.trim().equalsIgnoreCase("usuario_id")) {
            colunaOrdenacao = "usuario_id";
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
                    lista.add(new Admin(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    ));
                }
            }
        }
        return lista;
    }

    public Admin read(int id) throws SQLException {
        String sql = "SELECT * FROM admin WHERE id = ?";
        Conexao conexao = new Conexao();
        Admin admin = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    admin = new Admin(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return admin;
    }

    public Admin read(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM admin WHERE usuario_id = ?";
        Conexao conexao = new Conexao();
        Admin admin = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(email));

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    admin = new Admin(
                            rset.getInt("id"),
                            rset.getInt("usuario_id")
                    );
                }
            }
        }
        return admin;
    }

    public int update(Admin admin) throws SQLException {
        String sql = "UPDATE admin SET usuario_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, admin.getUsuarioId());
            pstmt.setInt(2, admin.getId());

            return pstmt.executeUpdate() > 0 ? 1 : 0;
        }
    }

    public int update(String nome, String email, String senha, int id) throws SQLException {
        String sql = "UPDATE admin SET usuario_id = ? WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(nome));
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0 ? 1 : 0;
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM admin WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0 ? 1 : 0;
        }
    }

    public int delete(String nome) throws SQLException {
        String sql = "DELETE FROM admin WHERE usuario_id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(nome));
            return pstmt.executeUpdate() > 0 ? 1 : 0;
        }
    }
}