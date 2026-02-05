package com.example.models;

import java.util.UUID;

public class Aluno {
    private int id;
    private String matricula;
    private int usuarioId;

    public Aluno(int usuarioId) {
        this.setUsuarioId(usuarioId);
        this.matricula = UUID.randomUUID().toString();
    }

    public Aluno(int id, String matricula, int usuarioId) {
        this.setId(id);
        this.matricula = matricula;
        this.setUsuarioId(usuarioId);
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

    public int getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("O ID de usuário não pode ser negativo");
        }
        this.usuarioId = usuarioId;
    }
}
