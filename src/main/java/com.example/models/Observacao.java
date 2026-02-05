package com.example.models;

import java.time.LocalDateTime;

public class Observacao {
    private int id;
    private String texto;
    private LocalDateTime dataEnvio;
    private int professorId;
    private int alunoId;

    public Observacao(String texto, int professorId, int alunoId) {
        this.setTexto(texto);
        this.setProfessorId(professorId);
        this.setAlunoId(alunoId);
        this.setDataEnvio(LocalDateTime.now());
    }

    public Observacao(int id, String texto, LocalDateTime dataEnvio, int professorId, int alunoId) {
        this.setId(id);
        this.setTexto(texto);
        this.setDataEnvio(dataEnvio);
        this.setProfessorId(professorId);
        this.setAlunoId(alunoId);
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

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        if (professorId <= 0) {
            throw new IllegalArgumentException("O ID do professor deve ser positivo.");
        }
        this.professorId = professorId;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        if (alunoId <= 0) {
            throw new IllegalArgumentException("O ID do aluno deve ser positivo.");
        }
        this.alunoId = alunoId;
    }
}