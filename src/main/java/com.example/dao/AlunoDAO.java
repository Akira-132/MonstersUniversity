package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Aluno;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AlunoDAO {

    public boolean create(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (cpf, matricula, id_usuario) VALUES (?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getCpf());
            pstmt.setString(2, aluno.getMatricula());
            pstmt.setInt(3, aluno.getFkUsuarioId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Aluno> read() throws SQLException {
        String sql = "SELECT a.id_aluno, a.cpf, a.matricula, a.id_usuario, u.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM aluno a INNER JOIN usuario u ON a.id_usuario = u.id_usuario ORDER BY a.id_aluno ASC";

        Conexao conexao = new Conexao();
        List<Aluno> listaAluno = new LinkedList<>();

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

                Aluno aluno = new Aluno(
                        rset.getInt("id_aluno"),
                        rset.getString("cpf"),
                        rset.getString("matricula"),
                        rset.getInt("id_usuario")
                );

                aluno.setUsuario(usuario);

                listaAluno.add(aluno);
            }
        }

        return listaAluno;
    }

    public Aluno readById(int id) throws SQLException {
        String sql = "SELECT a.id_aluno, a.cpf, a.matricula, a.id_usuario, u.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM aluno a INNER JOIN usuario u ON a.id_usuario = u.id_usuario WHERE a.id_aluno = ?";

        Conexao conexao = new Conexao();
        Aluno aluno = null;

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

                    aluno = new Aluno(
                            rset.getInt("id_aluno"),
                            rset.getString("cpf"),
                            rset.getString("matricula"),
                            rset.getInt("id_usuario")
                    );

                    aluno.setUsuario(usuario);
                }
            }
        }

        return aluno;
    }

    public Aluno readByMatricula(String matricula) throws SQLException {
        String sql = "SELECT a.id_aluno, a.cpf, a.matricula, a.id_usuario, u.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM aluno a INNER JOIN usuario u ON a.id_usuario = u.id_usuario WHERE a.matricula = ?";

        Conexao conexao = new Conexao();
        Aluno aluno = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricula);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );

                    aluno = new Aluno(
                            rset.getInt("id_aluno"),
                            rset.getString("cpf"),
                            rset.getString("matricula"),
                            rset.getInt("id_usuario")
                    );

                    aluno.setUsuario(usuario);
                }
            }
        }

        return aluno;
    }

    public Aluno readByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT a.id_aluno, a.cpf, a.matricula, a.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM aluno a INNER JOIN usuario u ON a.id_usuario = u.id_usuario WHERE a.id_usuario = ?";

        Conexao conexao = new Conexao();
        Aluno aluno = null;

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

                    aluno = new Aluno(
                            rset.getInt("id_aluno"),
                            rset.getString("cpf"),
                            rset.getString("matricula"),
                            rset.getInt("id_usuario")
                    );

                    aluno.setUsuario(usuario);
                }
            }
        }

        return aluno;
    }

    public Aluno readByCpf(String cpf) throws SQLException {
        String sql = "SELECT a.id_aluno, a.cpf, a.matricula, a.id_usuario, u.nome, u.sobrenome, u.email, u.senha FROM aluno a INNER JOIN usuario u ON a.id_usuario = u.id_usuario WHERE a.cpf = ?";

        Conexao conexao = new Conexao();
        Aluno aluno = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );

                    aluno = new Aluno(
                            rset.getInt("id_aluno"),
                            rset.getString("cpf"),
                            rset.getString("matricula"),
                            rset.getInt("id_usuario")
                    );

                    aluno.setUsuario(usuario);
                }
            }
        }

        return aluno;
    }

    public Usuario loginPorMatricula(String matricula, String senha) throws SQLException {
        String sql = "SELECT u.id_usuario, u.nome, u.sobrenome, u.email, u.senha " +
                "FROM aluno a " +
                "INNER JOIN usuario u ON a.id_usuario = u.id_usuario " +
                "WHERE a.matricula = ? AND u.senha = ?";

        Conexao conexao = new Conexao();
        Usuario usuario = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricula);
            pstmt.setString(2, senha);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha")
                    );
                }
            }
        }

        return usuario;
    }

    public int update(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET cpf = ?, matricula = ?, id_usuario = ? WHERE id_aluno = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getCpf());
            pstmt.setString(2, aluno.getMatricula());
            pstmt.setInt(3, aluno.getFkUsuarioId());
            pstmt.setInt(4, aluno.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = (SELECT id_usuario FROM aluno WHERE id_aluno = ?)";
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
