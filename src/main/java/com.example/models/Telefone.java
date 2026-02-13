package com.example.models;

public class Telefone {
    private int id;
    private String telefone;
    private int fkUsuarioId;

    public Telefone(String telefone, int fkUsuarioId) {
        this.setTelefone(telefone);
        this.setFkUsuarioId(fkUsuarioId);
    }

    public Telefone(int id, String telefone, int fkUsuarioId) {
        this.setId(id);
        this.setTelefone(telefone);
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

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        if (telefone == null) {
            throw new NullPointerException("O telefone não pode ser nulo.");
        }
        String telefoneLimpo = telefone.replaceAll("[^\\d]", "");
        validateTelefone(telefoneLimpo);
        this.telefone = telefoneLimpo;
    }

    public int getFkUsuarioId() { return fkUsuarioId; }
    public void setFkUsuarioId(int fkUsuarioId) {
        if (fkUsuarioId <= 0) throw new IllegalArgumentException("O ID de usuário deve ser positivo.");
        this.fkUsuarioId = fkUsuarioId;
    }

    private void validateTelefone(String telefoneLimpo) {
        int len = telefoneLimpo.length();
        if (len != 10 && len != 11) {
            throw new IllegalArgumentException("Telefone inválido. Deve conter 10 ou 11 dígitos (com DDD). Recebido: '" + telefoneLimpo + "'");
        }
    }
}
