package com.example.models;

public class Disciplina {
    private int id;
    private String nome;
    private int professorId;

    public Disciplina(String nome, int professorId) {
        this.setNome(nome);
        this.setProfessorId(professorId);
    }

    public Disciplina(int id, String nome, int professorId) {
        this.setId(id);
        this.setNome(nome);
        this.setProfessorId(professorId);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID não pode ser negativo");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if (nome == null) {
            throw new NullPointerException("O nome não pode ser nulo.");
        }
        if (nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode estar em branco.");
        }
        this.nome = nome;
    }

    public int getProfessorId() {
        return professorId;
    }
    public void setProfessorId(int professorId) {
        if (professorId <= 0) {
            throw new IllegalArgumentException("O ID de professor não pode ser negativo");
        }
        this.professorId = professorId;
    }
}
