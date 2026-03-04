package com.example.models;

import java.time.LocalDateTime;

public class Observacao {
    private int id;
    private String comentario;
    private LocalDateTime dataEnvio;
    private int fkProfessorId;
    private int fkAlunoId;
    private Professor professor;
    private Aluno aluno;

    public Observacao(String comentario, int fkProfessorId, int fkAlunoId) {
        this.setComentario(comentario);
        this.setFkProfessorId(fkProfessorId);
        this.setFkAlunoId(fkAlunoId);
        this.setDataEnvio(LocalDateTime.now());
    }

    public Observacao(int id, String comentario, LocalDateTime dataEnvio, int fkProfessorId, int fkAlunoId) {
        this.setId(id);
        this.setComentario(comentario);
        this.setDataEnvio(dataEnvio);
        this.setFkProfessorId(fkProfessorId);
        this.setFkAlunoId(fkAlunoId);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser positivo.");
        }
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        if (comentario == null) {
            throw new NullPointerException("O comentário não pode ser nulo.");
        }
        if (comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("O comentário não pode estar em branco.");
        }
        this.comentario = comentario;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }
    public void setDataEnvio(LocalDateTime dataEnvio) {
        if (dataEnvio == null) {
            throw new NullPointerException("A data de envio não pode ser nula.");
        }
        this.dataEnvio = dataEnvio;
    }

    public int getFkProfessorId() {
        return fkProfessorId;
    }
    public void setFkProfessorId(int fkProfessorId) {
        if (fkProfessorId <= 0) {
            throw new IllegalArgumentException("O ID do professor deve ser positivo.");
        }
        this.fkProfessorId = fkProfessorId;
    }

    public int getFkAlunoId() {
        return fkAlunoId;
    }
    public void setFkAlunoId(int fkAlunoId) {
        if (fkAlunoId <= 0) {
            throw new IllegalArgumentException("O ID do aluno deve ser positivo.");
        }
        this.fkAlunoId = fkAlunoId;
    }

    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Aluno getAluno() {
        return aluno;
    }
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}