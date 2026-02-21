package com.example.models;

import java.time.LocalDateTime;

public class Observacao {
    private int id;
    private String texto;
    private LocalDateTime dataEnvio;
    private int fkProfessorId;
    private int fkAlunoId;
    private Professor professor;
    private Aluno aluno;

    public Observacao(String texto, int fkProfessorId, int fkAlunoId) {
        this.setTexto(texto);
        this.setFkProfessorId(fkProfessorId);
        this.setFkAlunoId(fkAlunoId);
        this.setDataEnvio(LocalDateTime.now());
    }

    public Observacao(int id, String texto, LocalDateTime dataEnvio, int fkProfessorId, int fkAlunoId) {
        this.setId(id);
        this.setTexto(texto);
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

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        if (texto == null) {
            throw new NullPointerException("O texto não pode ser nulo.");
        }
        if (texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O texto não pode estar em branco.");
        }
        this.texto = texto;
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