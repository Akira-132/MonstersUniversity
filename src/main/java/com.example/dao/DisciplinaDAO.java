package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Disciplina;
import com.example.models.Professor;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DisciplinaDAO {

    public boolean create(Disciplina disciplina) throws SQLException {
        String sql = "INSERT INTO disciplina (nome, id_professor) VALUES (?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, disciplina.getNome());
            pstmt.setInt(2, disciplina.getFkProfessorId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Disciplina> read() throws SQLException {
        String sql = "SELECT d.id_disciplina, d.nome, d.id_professor, " +
                "p.id_professor, p.id_usuario, " +
                "u.id_usuario, u.nome, u.sobrenome, u.email, u.senha, u.tipo " +
                "FROM disciplina d " +
                "INNER JOIN professor p ON d.id_professor = p.id_professor " +
                "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "ORDER BY d.id_disciplina ASC";

        Conexao conexao = new Conexao();
        List<Disciplina> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Usuario usuario = new Usuario(
                        rset.getInt("id_usuario"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getString("tipo")
                );

                Professor professor = new Professor(
                        rset.getInt("id_professor"),
                        rset.getInt("id_usuario")
                );

                professor.setUsuario(usuario);

                Disciplina disciplina = new Disciplina(
                        rset.getInt("id_disciplina"),
                        rset.getString("nome"),
                        rset.getInt("id_professor")
                );

                disciplina.setProfessor(professor);

                lista.add(disciplina);
            }
        }

        return lista;
    }

    public Disciplina readById(int id) throws SQLException {
        String sql = "SELECT d.id_disciplina, d.nome, d.id_professor, " +
                "p.id_professor, p.id_usuario, " +
                "u.id_usuario, u.nome, u.sobrenome, u.email, u.senha, u.tipo " +
                "FROM disciplina d " +
                "INNER JOIN professor p ON d.id_professor = p.id_professor " +
                "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "WHERE d.id_disciplina = ?";

        Conexao conexao = new Conexao();
        Disciplina disciplina = null;

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
                            rset.getString("senha"),
                            rset.getString("tipo")
                    );

                    Professor professor = new Professor(
                            rset.getInt("id_professor"),
                            rset.getInt("id_usuario")
                    );

                    professor.setUsuario(usuario);

                    disciplina = new Disciplina(
                            rset.getInt("id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("id_professor")
                    );

                    disciplina.setProfessor(professor);
                }
            }
        }

        return disciplina;
    }

    public Disciplina readByNome(String nome) throws SQLException {
        String sql = "SELECT d.id_disciplina, d.nome, d.id_professor, " +
                "p.id_professor, p.id_usuario, " +
                "u.id_usuario, u.nome, u.sobrenome, u.email, u.senha, u.tipo " +
                "FROM disciplina d " +
                "INNER JOIN professor p ON d.id_professor = p.id_professor " +
                "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                "WHERE d.nome = ?";

        Conexao conexao = new Conexao();
        Disciplina disciplina = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Usuario usuario = new Usuario(
                            rset.getInt("id_usuario"),
                            rset.getString("nome"),
                            rset.getString("sobrenome"),
                            rset.getString("email"),
                            rset.getString("senha"),
                            rset.getString("tipo")
                    );

                    Professor professor = new Professor(
                            rset.getInt("id_professor"),
                            rset.getInt("id_usuario")
                    );

                    professor.setUsuario(usuario);

                    disciplina = new Disciplina(
                            rset.getInt("id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("id_professor")
                    );

                    disciplina.setProfessor(professor);
                }
            }
        }

        return disciplina;
    }

    public int update(Disciplina disciplina) throws SQLException {
        String sql = "UPDATE disciplina SET nome = ?, id_professor = ? WHERE id_disciplina = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, disciplina.getNome());
            pstmt.setInt(2, disciplina.getFkProfessorId());
            pstmt.setInt(3, disciplina.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id_disciplina = ?";
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
