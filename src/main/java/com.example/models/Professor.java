package com.example.models;

public class Professor {
    private int id;
    private int usuarioId;

    public Professor(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Professor(int id, int usuarioId) {
        this.setId(id);
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
