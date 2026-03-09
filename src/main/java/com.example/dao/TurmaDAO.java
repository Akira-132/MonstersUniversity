package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Turma;
import com.example.models.Disciplina;
import com.example.models.Aluno;
import com.example.models.Usuario;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TurmaDAO {

    public boolean create(Turma turma) throws SQLException {

        String sqlTurma = "INSERT INTO turma (periodo, sala, id_disciplina) VALUES (?, ?, ?)";
        String sqlRelacao = "INSERT INTO turma_aluno (id_turma, id_aluno) VALUES (?, ?)";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmtTurma = conn.prepareStatement(sqlTurma, Statement.RETURN_GENERATED_KEYS)) {

            pstmtTurma.setString(1, turma.getPeriodo());
            pstmtTurma.setString(2, turma.getSala());
            pstmtTurma.setInt(3, turma.getFkDisciplinaId());

            boolean criada = pstmtTurma.executeUpdate() > 0;

            if (criada && turma.getAlunos() != null && !turma.getAlunos().isEmpty()) {

                try (ResultSet generatedKeys = pstmtTurma.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int turmaId = generatedKeys.getInt(1);

                        try (PreparedStatement pstmtRel = conn.prepareStatement(sqlRelacao)) {
                            for (Aluno aluno : turma.getAlunos()) {
                                pstmtRel.setInt(1, turmaId);
                                pstmtRel.setInt(2, aluno.getId());
                                pstmtRel.executeUpdate();
                            }
                        }
                    }
                }
            }

            return criada;
        }
    }

    public List<Turma> read() throws SQLException {

        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina AS d_id_disciplina, d.nome, d.id_professor " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "ORDER BY d.nome ASC";

        Conexao conexao = new Conexao();
        List<Turma> listaTurma = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rset = pstmt.executeQuery()) {

            while (rset.next()) {

                Disciplina disciplina = new Disciplina(
                        rset.getInt("d_id_disciplina"),
                        rset.getString("nome"),
                        rset.getInt("id_professor")
                );

                Turma turma = new Turma(
                        rset.getInt("id_turma"),
                        rset.getString("sala"),
                        rset.getString("periodo"),
                        rset.getInt("id_disciplina")
                );

                turma.setDisciplina(disciplina);
                turma.setAlunos(findAlunosInTurma(conn, turma.getId()));

                listaTurma.add(turma);
            }
        }

        return listaTurma;
    }

    public Turma readById(int id) throws SQLException {

        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina AS d_id_disciplina, d.nome, d.id_professor " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "WHERE t.id_turma = ?";

        Conexao conexao = new Conexao();
        Turma turma = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Disciplina disciplina = new Disciplina(
                            rset.getInt("d_id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("id_professor")
                    );

                    turma = new Turma(
                            rset.getInt("id_turma"),
                            rset.getString("sala"),
                            rset.getString("periodo"),
                            rset.getInt("id_disciplina")
                    );

                    turma.setDisciplina(disciplina);
                    turma.setAlunos(findAlunosInTurma(conn, turma.getId()));
                }
            }
        }

        return turma;
    }

    public Turma readByTurma(String sala) throws SQLException {

        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina AS d_id_disciplina, d.nome, d.id_professor " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "WHERE t.sala = ?";

        Conexao conexao = new Conexao();
        Turma turma = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sala);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Disciplina disciplina = new Disciplina(
                            rset.getInt("d_id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("id_professor")
                    );

                    turma = new Turma(
                            rset.getInt("id_turma"),
                            rset.getString("sala"),
                            rset.getString("periodo"),
                            rset.getInt("id_disciplina")
                    );

                    turma.setDisciplina(disciplina);
                    turma.setAlunos(findAlunosInTurma(conn, turma.getId()));
                }
            }
        }

        return turma;
    }

    public List<Turma> readByDisciplinaId(int idDisciplina) throws SQLException {

        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina AS d_id_disciplina, d.nome, d.id_professor " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "WHERE t.id_disciplina = ? " +
                "ORDER BY t.sala ASC";

        Conexao conexao = new Conexao();
        List<Turma> listaTurma = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDisciplina);

            try (ResultSet rset = pstmt.executeQuery()) {

                while (rset.next()) {
                    Disciplina disciplina = new Disciplina(
                            rset.getInt("d_id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("id_professor")
                    );
                    Turma turma = new Turma(
                            rset.getInt("id_turma"),
                            rset.getString("sala"),
                            rset.getString("periodo"),
                            rset.getInt("id_disciplina")
                    );

                    turma.setDisciplina(disciplina);
                    turma.setAlunos(findAlunosInTurma(conn, turma.getId()));

                    listaTurma.add(turma);
                }
            }
        }

        return listaTurma;
    }

    public Turma readByAlunoId(int alunoId) throws SQLException {

        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina AS d_id_disciplina, d.nome, d.id_professor " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "INNER JOIN turma_aluno ta ON t.id_turma = ta.id_turma " +
                "WHERE ta.id_aluno = ?";

        Conexao conexao = new Conexao();
        Turma turma = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {

                    Disciplina disciplina = new Disciplina(
                            rset.getInt("d_id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("id_professor")
                    );

                    turma = new Turma(
                            rset.getInt("id_turma"),
                            rset.getString("sala"),
                            rset.getString("periodo"),
                            rset.getInt("id_disciplina")
                    );

                    turma.setDisciplina(disciplina);
                    turma.setAlunos(findAlunosInTurma(conn, turma.getId()));
                }
            }
        }

        return turma;
    }

    public List<Turma> readAllByAlunoId(int alunoId) throws SQLException {

        String sql = "SELECT t.id_turma, t.periodo, t.sala, t.id_disciplina, " +
                "d.id_disciplina AS d_id_disciplina, d.nome, d.id_professor " +
                "FROM turma t " +
                "INNER JOIN disciplina d ON t.id_disciplina = d.id_disciplina " +
                "INNER JOIN turma_aluno ta ON t.id_turma = ta.id_turma " +
                "WHERE ta.id_aluno = ?";

        Conexao conexao = new Conexao();
        List<Turma> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    Disciplina disciplina = new Disciplina(
                            rset.getInt("d_id_disciplina"),
                            rset.getString("nome"),
                            rset.getInt("id_professor")
                    );

                    Turma turma = new Turma(
                            rset.getInt("id_turma"),
                            rset.getString("sala"),
                            rset.getString("periodo"),
                            rset.getInt("id_disciplina")
                    );

                    turma.setDisciplina(disciplina);
                    lista.add(turma);
                }
            }
        }

        return lista;
    }

    public int update(Turma turma) throws SQLException {

        String sqlUpdate = "UPDATE turma SET periodo = ?, sala = ?, id_disciplina = ? WHERE id_turma = ?";
        String sqlDeleteRel = "DELETE FROM turma_aluno WHERE id_turma = ?";
        String sqlInsertRel = "INSERT INTO turma_aluno (id_turma, id_aluno) VALUES (?, ?)";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            pstmt.setString(1, turma.getPeriodo());
            pstmt.setString(2, turma.getSala());
            pstmt.setInt(3, turma.getFkDisciplinaId());
            pstmt.setInt(4, turma.getId());

            int linhas = pstmt.executeUpdate();

            try (PreparedStatement pstmtDelete = conn.prepareStatement(sqlDeleteRel)) {
                pstmtDelete.setInt(1, turma.getId());
                pstmtDelete.executeUpdate();
            }

            if (turma.getAlunos() != null) {

                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertRel)) {

                    for (Aluno aluno : turma.getAlunos()) {

                        pstmtInsert.setInt(1, turma.getId());
                        pstmtInsert.setInt(2, aluno.getId());
                        pstmtInsert.executeUpdate();
                    }
                }
            }

            return linhas;
        }
    }

    public int deleteById(int id) throws SQLException {

        String sqlDeleteRel = "DELETE FROM turma_aluno WHERE id_turma = ?";
        String sqlDeleteTurma = "DELETE FROM turma WHERE id_turma = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar()) {

            try (PreparedStatement pstmtRel = conn.prepareStatement(sqlDeleteRel)) {
                pstmtRel.setInt(1, id);
                pstmtRel.executeUpdate();
            }

            try (PreparedStatement pstmtTurma = conn.prepareStatement(sqlDeleteTurma)) {
                pstmtTurma.setInt(1, id);
                return pstmtTurma.executeUpdate();
            }
        }
    }

    public int deleteBySala(String sala) throws SQLException {

        String sqlBuscarId = "SELECT id_turma FROM turma WHERE sala = ?";
        String sqlDeleteRel = "DELETE FROM turma_aluno WHERE id_turma = ?";
        String sqlDeleteTurma = "DELETE FROM turma WHERE sala = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmtBuscar = conn.prepareStatement(sqlBuscarId)) {

            pstmtBuscar.setString(1, sala);

            try (ResultSet rset = pstmtBuscar.executeQuery()) {

                if (rset.next()) {

                    int turmaId = rset.getInt("id_turma");

                    try (PreparedStatement pstmtRel = conn.prepareStatement(sqlDeleteRel)) {
                        pstmtRel.setInt(1, turmaId);
                        pstmtRel.executeUpdate();
                    }
                }
            }

            try (PreparedStatement pstmtTurma = conn.prepareStatement(sqlDeleteTurma)) {
                pstmtTurma.setString(1, sala);
                return pstmtTurma.executeUpdate();
            }
        }
    }

    private List<Aluno> findAlunosInTurma(Connection conn, int turmaId) throws SQLException {
        String sql = "SELECT a.id_aluno, a.cpf, a.matricula, a.id_usuario, " +
                "u.nome, u.sobrenome, u.email, u.senha " +
                "FROM turma_aluno ta " +
                "INNER JOIN aluno a ON ta.id_aluno = a.id_aluno " +
                "INNER JOIN usuario u ON a.id_usuario = u.id_usuario " +
                "WHERE ta.id_turma = ? " +
                "ORDER BY u.nome ASC";

        List<Aluno> lista = new LinkedList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, turmaId);

            try (ResultSet rset = pstmt.executeQuery()) {
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

                    lista.add(aluno);
                }
            }
        }
        return lista;
    }

    public boolean addAlunoInTurma(int turmaId, int alunoId) throws SQLException {

        String sql = "INSERT INTO turma_aluno (id_turma, id_aluno) VALUES (?, ?)";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, turmaId);
            pstmt.setInt(2, alunoId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean removeAlunoFromTurma(int turmaId, int alunoId) throws SQLException {

        String sql = "DELETE FROM turma_aluno WHERE id_turma = ? AND id_aluno = ?";

        Conexao conexao = new Conexao();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, turmaId);
            pstmt.setInt(2, alunoId);

            return pstmt.executeUpdate() > 0;
        }
    }

}
