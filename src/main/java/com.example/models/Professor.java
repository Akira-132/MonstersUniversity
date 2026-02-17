package com.example.models;

public class Professor {
    private int id;
    private int fkUsuarioId;

    public Professor(int fkUsuarioId) {
        this.fkUsuarioId = fkUsuarioId;
    }

    public Professor(int id, int fkUsuarioId) {
        this.setId(id);
        this.setFkUsuarioId(fkUsuarioId);
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

    public int getFkUsuarioId() {
        return fkUsuarioId;
    }
    public void setFkUsuarioId(int fkUsuarioId) {
        if (fkUsuarioId <= 0) {
            throw new IllegalArgumentException("O ID de usuário não pode ser negativo");
        }
        this.fkUsuarioId = fkUsuarioId;
    }
}
