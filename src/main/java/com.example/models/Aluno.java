package com.example.models;

public class Aluno {
    private int id;
    private String matricula;
    private int usuarioId;

    public Aluno(String matricula, int usuarioId) {
        this.setMatricula(matricula);
    }

    public Aluno(int id, String matricula, int usuarioId) {
        this.setId(id);
        this.setMatricula(matricula);
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

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getUsuarioId() {
        return id;
    }
    public void setUsuarioId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID não pode ser negativo");
        }
        this.id = id;
    }
}
