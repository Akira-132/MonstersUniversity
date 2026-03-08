package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Admin;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AdminDAO {

    public boolean create(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (id_usuario) VALUES (?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, admin.getFkUsuarioId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Admin> read() throws SQLException {
        String sql = "SELECT a.id_admin, a.id_usuario, u.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM admin a INNER JOIN usuario u ON a.id_usuario = u.id_usuario ORDER BY u.nome ASC";

        Conexao conexao = new Conexao();
        List<Admin> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Usuario usuario = new Usuario(
                        rset.getInt("id_usuario"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getString("email"),
                        rset.getString("senha")
                );

                Admin admin = new Admin(
                        rset.getInt("id_admin"),
                        rset.getInt("id_usuario")
                );

                admin.setUsuario(usuario);

                lista.add(admin);
            }
        }

        return lista;
    }

    public Admin readById(int id) throws SQLException {
        String sql = "SELECT a.id_admin, a.id_usuario, u.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM admin a INNER JOIN usuario u ON a.id_usuario = u.id_usuario WHERE a.id_admin = ?";

        Conexao conexao = new Conexao();
        Admin admin = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );

                    admin = new Admin(
                            rset.getInt("id_admin"),
                            rset.getInt("id_usuario")
                    );

                    admin.setUsuario(usuario);
                }
            }
        }

        return admin;
    }

    public Admin readByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT a.id_admin, a.id_usuario, u.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM admin a INNER JOIN usuario u ON a.id_usuario = u.id_usuario WHERE a.id_usuario = ?";

        Conexao conexao = new Conexao();
        Admin admin = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );

                    admin = new Admin(
                            rset.getInt("id_admin"),
                            rset.getInt("id_usuario")
                    );

                    admin.setUsuario(usuario);
                }
            }
        }

        return admin;
    }

    public int update(Admin admin) throws SQLException {
        String sql = "UPDATE admin SET id_usuario = ? WHERE id_admin = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, admin.getFkUsuarioId());
            pstmt.setInt(2, admin.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = (SELECT id_usuario FROM admin WHERE id_admin = ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByUsuarioId(int usuarioId) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            return pstmt.executeUpdate();
        }
    }
}
