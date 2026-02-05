package com.example.models;
public class Boletim {
    private int id;
    private double n1;
    private double n2;
    private int alunoId;
    private int disciplinaId;

    public Boletim(double n1, double n2, int alunoId, int disciplinaId) {
        this.setN1(n1);
        this.setN2(n2);
        this.setAlunoId(alunoId);
        this.setDisciplinaId(disciplinaId);
    }

    public Boletim(int id, double n1, double n2, int alunoId, int disciplinaId) {
        this.setId(id);
        this.setN1(n1);
        this.setN2(n2);
        this.setAlunoId(alunoId);
        this.setDisciplinaId(disciplinaId);
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

    public double getN1() {
        return n1;
    }
    public void setN1(double n1) {
        if (n1 < 0 || n1 > 10) {
            throw new IllegalArgumentException("A N1 não pode ser abaixo de 0 nem acima de 10");
        }
        this.n1 = Math.round(n1 * 10.0) / 10.0;
    }

    public double getN2() {
        return n2;
    }
    public void setN2(double n2) {
        if (n2 < 0 || n2 > 10) {
            throw new IllegalArgumentException("A N2 não pode ser abaixo de 0 nem acima de 10");
        }
        this.n2 = Math.round(n2 * 10.0) / 10.0;
    }

    public int getAlunoId() {
        return alunoId;
    }
    public void setAlunoId(int alunoId) {
        if (alunoId <= 0) {
            throw new IllegalArgumentException("O ID de aluno não pode ser negativo");
        }
        this.alunoId = alunoId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }
    public void setDisciplinaId(int disciplinaId) {
        if (disciplinaId <= 0) {
            throw new IllegalArgumentException("O ID de disciplina não pode ser negativo");
        }
        this.disciplinaId = disciplinaId;
    }
}
