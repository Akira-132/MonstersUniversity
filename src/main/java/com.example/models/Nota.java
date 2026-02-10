package com.example.models;
public class Nota {
    private int id;
    private String tipo;
    private int semestre;
    private double nota;
    private int fkAlunoId;
    private int fkDisciplinaId;

    public Nota(String tipo, int semestre, double nota, int fkAlunoId, int fkDisciplinaId) {
        this.setTipo(tipo);
        this.setSemestre(semestre);
        this.setNota(nota);
        this.setFkAlunoId(fkAlunoId);
        this.setFkDisciplinaId(fkDisciplinaId);
    }

    public Nota(int id, String tipo, int semestre, double nota, int fkAlunoId, int fkDisciplinaId) {
        this.setId(id);
        this.setTipo(tipo);
        this.setSemestre(semestre);
        this.setNota(nota);
        this.setFkAlunoId(fkAlunoId);
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

    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("A nota não pode ser abaixo de 0 nem acima de 10");
        }
        this.nota = Math.round(nota * 10.0) / 10.0;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) {
        if (tipo == null) {
            throw new NullPointerException("O tipo não pode ser nulo.");
        }
        if (tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo não pode estar em branco.");
        }
        if (!tipo.equals("N1") && !tipo.equals("N2")) {
            throw new IllegalArgumentException("O tipo só pode ser N1 ou N2");
        }
        this.tipo = tipo;
    }

    public int getSemestre() {
        return semestre;
    }
    public void setSemestre(int semestre) {
        if (semestre < 1 || semestre > 2 ) {
            throw new IllegalArgumentException("O semestre só pode ser 1 ou 2");
        }
        this.semestre = semestre;
    }

    public int getFkAlunoId() {
        return fkAlunoId;
    }
    public void setFkAlunoId(int fkAlunoId) {
        if (fkAlunoId <= 0) {
            throw new IllegalArgumentException("O ID de aluno não pode ser negativo");
        }
        this.fkAlunoId = fkAlunoId;
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
