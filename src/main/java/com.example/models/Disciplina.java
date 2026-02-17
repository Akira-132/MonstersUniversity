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
            throw new IllegalArgumentException("O ID não pode ser negativo ou zero.");
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
        this.nome = nome.trim();
    }

    public int getFkProfessorId() {
        return fkProfessorId;
    }

    public void setFkProfessorId(int fkProfessorId) {
        if (fkProfessorId <= 0) {
            throw new IllegalArgumentException("O ID de professor não pode ser negativo ou zero.");
        }

        this.fkProfessorId = fkProfessorId;

        if (this.professor != null && this.professor.getId() != fkProfessorId) {
            this.professor = null;
        }
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        if (professor == null) {
            throw new NullPointerException("O professor não pode ser nulo.");
        }

        if (professor.getId() <= 0) {
            throw new IllegalArgumentException("O professor deve possuir ID válido.");
        }

        this.professor = professor;
        this.fkProfessorId = professor.getId(); // sincroniza automaticamente
    }
}
