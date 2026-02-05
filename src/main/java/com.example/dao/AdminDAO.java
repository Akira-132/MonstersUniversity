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

    public Admin readById(int id) throws SQLException {
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

    public Admin readByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM admin WHERE usuario_id = ?";
        Conexao conexao = new Conexao();
        Admin admin = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);

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

            return pstmt.executeUpdate();
        }
    }


    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM admin WHERE id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByUsuarioId(int usuarioId) throws SQLException {
        String sql = "DELETE FROM admin WHERE usuario_id = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            return pstmt.executeUpdate();
        }
    }
}