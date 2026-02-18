package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class NotaDAO {

    public boolean create(Nota nota) throws SQLException {
        String sql = "INSERT INTO nota (tipo, semestre, ano, nota, id_aluno, id_disciplina) VALUES (?, ?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nota.getTipo());
            pstmt.setInt(2, nota.getSemestre());
            pstmt.setInt(3, nota.getAno());
            pstmt.setDouble(4, nota.getNota());
            pstmt.setInt(5, nota.getFkAlunoId());
            pstmt.setInt(6, nota.getFkDisciplinaId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Nota> read() throws SQLException {

        String sql =
                "SELECT n.id_nota, n.tipo, n.semestre, n.ano, n.nota, n.id_aluno, n.id_disciplina, " +
                        "a.id_aluno, a.cpf, a.matricula, a.id_usuario AS aluno_usuario_id, " +
                        "ua.id_usuario AS usuario_aluno_id, ua.nome AS aluno_nome, ua.sobrenome AS aluno_sobrenome, ua.email AS aluno_email, ua.senha AS aluno_senha, ua.tipo AS aluno_tipo, " +
                        "d.id_disciplina, d.nome AS disciplina_nome, d.id_professor, " +
                        "p.id_professor, p.id_usuario AS professor_usuario_id, " +
                        "up.id_usuario AS usuario_professor_id, up.nome AS professor_nome, up.sobrenome AS professor_sobrenome, up.email AS professor_email, up.senha AS professor_senha, up.tipo AS professor_tipo " +
                        "FROM nota n " +
                        "INNER JOIN aluno a ON n.id_aluno = a.id_aluno " +
                        "INNER JOIN usuario ua ON a.id_usuario = ua.id_usuario " +
                        "INNER JOIN disciplina d ON n.id_disciplina = d.id_disciplina " +
                        "INNER JOIN professor p ON d.id_professor = p.id_professor " +
                        "INNER JOIN usuario up ON p.id_usuario = up.id_usuario " +
                        "ORDER BY n.id_nota ASC";

        Conexao conexao = new Conexao();
        List<Nota> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {
                lista.add(notaBuilder(rset));
            }
        }

        return lista;
    }

    public Nota readById(int id) throws SQLException {

        String sql =
                "SELECT n.id_nota, n.tipo, n.semestre, n.ano, n.nota, n.id_aluno, n.id_disciplina, " +
                        "a.id_aluno, a.cpf, a.matricula, a.id_usuario AS aluno_usuario_id, " +
                        "ua.id_usuario AS usuario_aluno_id, ua.nome AS aluno_nome, ua.sobrenome AS aluno_sobrenome, ua.email AS aluno_email, ua.senha AS aluno_senha, ua.tipo AS aluno_tipo, " +
                        "d.id_disciplina, d.nome AS disciplina_nome, d.id_professor, " +
                        "p.id_professor, p.id_usuario AS professor_usuario_id, " +
                        "up.id_usuario AS usuario_professor_id, up.nome AS professor_nome, up.sobrenome AS professor_sobrenome, up.email AS professor_email, up.senha AS professor_senha, up.tipo AS professor_tipo " +
                        "FROM nota n " +
                        "INNER JOIN aluno a ON n.id_aluno = a.id_aluno " +
                        "INNER JOIN usuario ua ON a.id_usuario = ua.id_usuario " +
                        "INNER JOIN disciplina d ON n.id_disciplina = d.id_disciplina " +
                        "INNER JOIN professor p ON d.id_professor = p.id_professor " +
                        "INNER JOIN usuario up ON p.id_usuario = up.id_usuario " +
                        "WHERE n.id_nota = ?";

        Conexao conexao = new Conexao();
        Nota nota = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    nota = notaBuilder(rset);
                }
            }
        }

        return nota;
    }

    public List<Nota> readByAlunoId(int alunoId) throws SQLException {

        String sql = "SELECT id_nota, tipo, semestre, ano, nota, id_aluno, id_disciplina " +
                "FROM nota WHERE id_aluno = ? ORDER BY id_nota ASC";

        Conexao conexao = new Conexao();
        List<Nota> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {

                    Nota nota = new Nota(
                            rset.getInt("id_nota"),
                            rset.getString("tipo"),
                            rset.getInt("semestre"),
                            rset.getInt("ano"),
                            rset.getDouble("nota"),
                            rset.getInt("id_aluno"),
                            rset.getInt("id_disciplina")
                    );

                    lista.add(nota);
                }
            }
        }

        return lista;
    }

    public List<Nota> readByDisciplinaId(int disciplinaId) throws SQLException {

        String sql = "SELECT id_nota, tipo, semestre, ano, nota, id_aluno, id_disciplina " +
                "FROM nota WHERE id_disciplina = ? ORDER BY id_nota ASC";

        Conexao conexao = new Conexao();
        List<Nota> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, disciplinaId);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {

                    Nota nota = new Nota(
                            rset.getInt("id_nota"),
                            rset.getString("tipo"),
                            rset.getInt("semestre"),
                            rset.getInt("ano"),
                            rset.getDouble("nota"),
                            rset.getInt("id_aluno"),
                            rset.getInt("id_disciplina")
                    );

                    lista.add(nota);
                }
            }
        }

        return lista;
    }

    public int update(Nota nota) throws SQLException {
        String sql = "UPDATE nota SET tipo = ?, semestre = ?, ano = ?, nota = ?, id_aluno = ?, id_disciplina = ? WHERE id_nota = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nota.getTipo());
            pstmt.setInt(2, nota.getSemestre());
            pstmt.setInt(3, nota.getAno());
            pstmt.setDouble(4, nota.getNota());
            pstmt.setInt(5, nota.getFkAlunoId());
            pstmt.setInt(6, nota.getFkDisciplinaId());
            pstmt.setInt(7, nota.getId());

            return pstmt.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM nota WHERE id_nota = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByAlunoId(int alunoId) throws SQLException {
        String sql = "DELETE FROM nota WHERE id_aluno = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            return pstmt.executeUpdate();
        }
    }

    public int deleteByDisciplinaId(int disciplinaId) throws SQLException {
        String sql = "DELETE FROM nota WHERE id_disciplina = ?";
        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, disciplinaId);
            return pstmt.executeUpdate();
        }
    }

    private Nota notaBuilder(ResultSet rset) throws SQLException {

        Usuario usuarioAluno = new Usuario(
                rset.getInt("usuario_aluno_id"),
                rset.getString("aluno_nome"),
                rset.getString("aluno_sobrenome"),
                rset.getString("aluno_email"),
                rset.getString("aluno_senha"),
                rset.getString("aluno_tipo")
        );

        Aluno aluno = new Aluno(
                rset.getInt("id_aluno"),
                rset.getString("cpf"),
                rset.getString("matricula"),
                rset.getInt("aluno_usuario_id")
        );
        aluno.setUsuario(usuarioAluno);

        Usuario usuarioProfessor = new Usuario(
                rset.getInt("usuario_professor_id"),
                rset.getString("professor_nome"),
                rset.getString("professor_sobrenome"),
                rset.getString("professor_email"),
                rset.getString("professor_senha"),
                rset.getString("professor_tipo")
        );

        Professor professor = new Professor(
                rset.getInt("id_professor"),
                rset.getInt("professor_usuario_id")
        );
        professor.setUsuario(usuarioProfessor);

        Disciplina disciplina = new Disciplina(
                rset.getInt("id_disciplina"),
                rset.getString("disciplina_nome"),
                rset.getInt("id_professor")
        );
        disciplina.setProfessor(professor);

        Nota nota = new Nota(
                rset.getInt("id_nota"),
                rset.getString("tipo"),
                rset.getInt("semestre"),
                rset.getInt("ano"),
                rset.getDouble("nota"),
                rset.getInt("id_aluno"),
                rset.getInt("id_disciplina")
        );

        nota.setAluno(aluno);
        nota.setDisciplina(disciplina);

        return nota;
    }
}
