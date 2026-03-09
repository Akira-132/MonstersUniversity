package com.example.dao;

import com.example.controllers.Conexao;
import com.example.models.Boletim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BoletimDAO {

    public List<Boletim> readByAlunoId(int idAluno) throws SQLException {
        String sql = "SELECT mf.id_aluno, mf.nome_completo, mf.disciplina, " +
                "MAX(CASE WHEN mp.tipo = 'P1' THEN mp.media END) AS media_p1, " +
                "MAX(CASE WHEN mp.tipo = 'P2' THEN mp.media END) AS media_p2, " +
                "mf.media_final, mf.situacao, mf.semestre, mf.ano " +
                "FROM Media_Final mf " +
                "LEFT JOIN Media_Por_P1_P2 mp " +
                "ON mf.id_aluno = mp.Id_aluno " +
                "AND mf.disciplina = mp.disciplina " +
                "AND mf.semestre = mp.Semestre " +
                "AND mf.ano = mp.ano " +
                "WHERE mf.id_aluno = ? " +
                "GROUP BY mf.id_aluno, mf.nome_completo, mf.disciplina, mf.media_final, mf.situacao, mf.semestre, mf.ano " +
                "ORDER BY mf.ano DESC, mf.semestre DESC, mf.disciplina";

        Conexao conexao = new Conexao();
        List<Boletim> lista = new LinkedList<>();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAluno);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    Boletim boletim = new Boletim(
                            rset.getInt("id_aluno"),
                            rset.getString("nome_completo"),
                            rset.getString("disciplina"),
                            rset.getObject("media_p1") == null ? null : rset.getDouble("media_p1"),
                            rset.getObject("media_p2") == null ? null : rset.getDouble("media_p2"),
                            rset.getObject("media_final") == null ? null : rset.getDouble("media_final"),
                            rset.getString("situacao"),
                            rset.getInt("semestre"),
                            rset.getInt("ano")
                    );
                    lista.add(boletim);
                }
            }
        }
        return lista;
    }
}