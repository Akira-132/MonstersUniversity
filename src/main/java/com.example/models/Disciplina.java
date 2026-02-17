package com.example.models;

public class Disciplina {
    private int id;
    private String nome;
    private int fkProfessorId;
    private Professor professor;

    public Disciplina(String nome, int fkProfessorId) {
        this.setNome(nome);
        this.setFkProfessorId(fkProfessorId);
    }

    public Disciplina(int id, String nome, int fkProfessorId) {
        this.setId(id);
        this.setNome(nome);
        this.setFkProfessorId(fkProfessorId);
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

    public int getFkProfessorId() {
        return fkProfessorId;
    }
    public void setFkProfessorId(int fkProfessorId) {
        if (fkProfessorId <= 0) {
            throw new IllegalArgumentException("O ID de professor não pode ser negativo");
        }
        this.fkProfessorId = fkProfessorId;
    }

    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}