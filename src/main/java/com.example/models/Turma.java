package com.example.models;

public class Turma {
    private int id;
    private String periodo;
    private String sala;
    private int fkDisciplinaId;

    public Turma(String periodo, String sala, int fkDisciplinaId) {
        this.setPeriodo(periodo);
        this.setSala(sala);
        this.setFkDisciplinaId(fkDisciplinaId);
    }

    public Turma(int id, String sala, String periodo, int fkDisciplinaId) {
        this.setId(id);
        this.setPeriodo(periodo);
        this.setSala(sala);
        this.setFkDisciplinaId(fkDisciplinaId);
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

    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        if (periodo == null) {
            throw new NullPointerException("O período não pode ser nulo.");
        }
        if (periodo.trim().isEmpty()) {
            throw new IllegalArgumentException("O período não pode estar em branco.");
        }
        String periodoLower = periodo.toLowerCase();
        if (!periodoLower.equals("manhã") && !periodoLower.equals("tarde") && !periodoLower.equals("noite")) {
            throw new IllegalArgumentException("O período escolhido não é uma opção válida");
        }
        this.periodo = periodo;
    }

    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        if (sala == null) {
            throw new NullPointerException("A sala não pode ser nulo.");
        }
        if (sala.trim().isEmpty()) {
            throw new IllegalArgumentException("A sala não pode estar em branco.");
        }
        if (sala.length() > 2) {
            throw new IllegalArgumentException("A sala tem um limite de 2 caracteres");
        }
        this.sala = sala;
    }

    public int getFkDisciplinaId() {
        return fkDisciplinaId;
    }
    public void setFkDisciplinaId(int fkDisciplinaId) {
        if (fkDisciplinaId <= 0) {
            throw new IllegalArgumentException("O ID de disciplina não pode ser negativo");
        }
        this.fkDisciplinaId = fkDisciplinaId;
    }
}
