package com.example.models;

import java.util.UUID;

public class Aluno {
    private int id;
    private String cpf;
    private String matricula;
    private int fkUsuarioId;
    private Usuario usuario;

    public Aluno(String cpf, int fkUsuarioId) {
        this.setCpf(cpf);
        this.matricula = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.setFkUsuarioId(fkUsuarioId);
    }

    public Aluno(int id, String cpf, String matricula, int fkUsuarioId) {
        this.setId(id);
        this.setCpf(cpf);
        this.setMatricula(matricula);
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

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        if (cpf == null) {
            throw new NullPointerException("O CPF não pode ser nulo.");
        }
        String cpfLimpo = cpf.replaceAll("[^\\d]", "");
        validateCpf(cpfLimpo);
        this.cpf = cpfLimpo;
    }

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void validateCpf(String cpfLimpo) {
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
    }
}