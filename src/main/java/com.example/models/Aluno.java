package com.example.models;

import java.util.UUID;

public class Aluno {
    private int id;
    private String cpf;
    private String matricula;
    private int fkUsuarioId;

    public Aluno(String cpf, int fkUsuarioId) {
        this.setCpf(cpf);
        this.setFkUsuarioId(fkUsuarioId);
        this.matricula = UUID.randomUUID().toString();
    }

    public Aluno(int id, String cpf, String matricula, int fkUsuarioId) {
        this.setId(id);
        this.setCpf(cpf);
        this.matricula = matricula;
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

    public int getFkUsuarioId() {
        return fkUsuarioId;
    }
    public void setFkUsuarioId(int fkUsuarioId) {
        if (fkUsuarioId <= 0) {
            throw new IllegalArgumentException("O ID de usuário não pode ser negativo");
        }
        this.fkUsuarioId = fkUsuarioId;
    }

    private void validateCpf(String cpfLimpo) {
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos (após remover formatação). Recebido: '" + cpfLimpo + "'.");
        }
    }
}
