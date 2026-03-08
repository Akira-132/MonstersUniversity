package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ObservacaoDAO {

    public boolean create(Observacao observacao) throws SQLException {

        String sql = "INSERT INTO observacoes (comentario, data_envio, id_professor, id_aluno) VALUES (?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, observacao.getComentario());
            pstmt.setTimestamp(2, Timestamp.valueOf(observacao.getDataEnvio()));
            pstmt.setInt(3, observacao.getFkProfessorId());
            pstmt.setInt(4, observacao.getFkAlunoId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Observacao> read() throws SQLException {

        String sql =
                "SELECT o.id_observacao, o.comentario, o.data_envio, o.id_professor, o.id_aluno, " +
                        "p.id_professor, p.id_usuario AS professor_usuario_id, " +
                        "up.id_usuario AS usuario_professor_id, up.nome AS professor_nome, up.sobrenome AS professor_sobrenome, up.email AS professor_email, up.senha AS professor_senha, " +
                        "a.id_aluno, a.cpf, a.matricula, a.id_usuario AS aluno_usuario_id, " +
                        "ua.id_usuario AS usuario_aluno_id, ua.nome AS aluno_nome, ua.sobrenome AS aluno_sobrenome, ua.email AS aluno_email, ua.senha AS aluno_senha " +
                        "FROM observacoes o " +
                        "INNER JOIN professor p ON o.id_professor = p.id_professor " +
                        "INNER JOIN usuario up ON p.id_usuario = up.id_usuario " +
                        "INNER JOIN aluno a ON o.id_aluno = a.id_aluno " +
                        "INNER JOIN usuario ua ON a.id_usuario = ua.id_usuario " +
                        "ORDER BY o.comentario ASC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                lista.add(observacaoBuilder(rset));
            }
        }

        return lista;
    }

    public Observacao readById(int id) throws SQLException {

        String sql =
                "SELECT o.id_observacao, o.comentario, o.data_envio, o.id_professor, o.id_aluno, " +
                        "p.id_professor, p.id_usuario AS professor_usuario_id, " +
                        "up.id_usuario AS usuario_professor_id, up.nome AS professor_nome, up.sobrenome AS professor_sobrenome, up.email AS professor_email, up.senha AS professor_senha, " +
                        "a.id_aluno, a.cpf, a.matricula, a.id_usuario AS aluno_usuario_id, " +
                        "ua.id_usuario AS usuario_aluno_id, ua.nome AS aluno_nome, ua.sobrenome AS aluno_sobrenome, ua.email AS aluno_email, ua.senha AS aluno_senha " +
                        "FROM observacoes o " +
                        "INNER JOIN professor p ON o.id_professor = p.id_professor " +
                        "INNER JOIN usuario up ON p.id_usuario = up.id_usuario " +
                        "INNER JOIN aluno a ON o.id_aluno = a.id_aluno " +
                        "INNER JOIN usuario ua ON a.id_usuario = ua.id_usuario " +
                        "WHERE o.id_observacao = ?";

        Conexao conexao = new Conexao();
        Observacao observacao = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    observacao = observacaoBuilder(rset);
                }
            }
        }

        return observacao;
    }

    public List<Observacao> readByAlunoId(int alunoId) throws SQLException {

        String sql = "SELECT id_observacao, comentario, data_envio, id_professor, id_aluno " +
                "FROM observacoes WHERE id_aluno = ? ORDER BY comentario ASC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {

                    Observacao observacao = new Observacao(
                            rset.getInt("id_observacao"),
                            rset.getString("comentario"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("id_professor"),
                            rset.getInt("id_aluno")
                    );

                    lista.add(observacao);
                }
            }
        }

        return lista;
    }

    public List<Observacao> readByProfessorId(int professorId) throws SQLException {

        String sql = "SELECT id_observacao, comentario, data_envio, id_professor, id_aluno " +
                "FROM observacoes WHERE id_professor = ? ORDER BY comentario ASC";

        Conexao conexao = new Conexao();
        List<Observacao> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professorId);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {

                    Observacao observacao = new Observacao(
                            rset.getInt("id_observacao"),
                            rset.getString("comentario"),
                            rset.getTimestamp("data_envio").toLocalDateTime(),
                            rset.getInt("id_professor"),
                            rset.getInt("id_aluno")
                    );

                    lista.add(observacao);
                }
            }
        }

        return lista;
    }

    public int update(Observacao observacao) throws SQLException {

        String sql = "UPDATE observacoes SET comentario = ?, data_envio = ?, id_professor = ?, id_aluno = ? WHERE id_observacao = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, observacao.getComentario());
            pstmt.setTimestamp(2, Timestamp.valueOf(observacao.getDataEnvio()));
            pstmt.setInt(3, observacao.getFkProfessorId());
            pstmt.setInt(4, observacao.getFkAlunoId());
            pstmt.setInt(5, observacao.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {

        String sql = "DELETE FROM observacoes WHERE id_observacao = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByAlunoId(int alunoId) throws SQLException {

        String sql = "DELETE FROM observacoes WHERE id_aluno = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByProfessorId(int professorId) throws SQLException {

        String sql = "DELETE FROM observacoes WHERE id_professor = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, professorId);
            return pstmt.executeUpdate();
        }
    }

    private Observacao observacaoBuilder(ResultSet rset) throws SQLException {

        Usuario usuarioProfessor = new Usuario(
                rset.getInt("usuario_professor_id"),
                rset.getString("professor_nome"),
                rset.getString("professor_sobrenome"),
                rset.getString("professor_email"),
                rset.getString("professor_senha")
        );

        Professor professor = new Professor(
                rset.getInt("id_professor"),
                rset.getInt("professor_usuario_id")
        );
        professor.setUsuario(usuarioProfessor);

        Usuario usuarioAluno = new Usuario(
                rset.getInt("usuario_aluno_id"),
                rset.getString("aluno_nome"),
                rset.getString("aluno_sobrenome"),
                rset.getString("aluno_email"),
                rset.getString("aluno_senha")
        );

        Aluno aluno = new Aluno(
                rset.getInt("id_aluno"),
                rset.getString("cpf"),
                rset.getString("matricula"),
                rset.getInt("aluno_usuario_id")
        );
        aluno.setUsuario(usuarioAluno);

        Observacao observacao = new Observacao(
                rset.getInt("id_observacao"),
                rset.getString("comentario"),
                rset.getTimestamp("data_envio").toLocalDateTime(),
                rset.getInt("id_professor"),
                rset.getInt("id_aluno")
        );

        observacao.setProfessor(professor);
        observacao.setAluno(aluno);

        return observacao;
    }
}
