package com.example.models;

public class TurmaAluno {
    private int id;
    private int fkAlunoId;
    private int fkTurmaId;

    private Aluno aluno;
    private Turma turma;

    public TurmaAluno(int fkAlunoId, int fkTurmaId) {
        this.setFkAlunoId(fkAlunoId);
        this.setFkTurmaId(fkTurmaId);
    }

    public TurmaAluno(int id, int fkAlunoId, int fkTurmaId) {
        this.setId(id);
        this.setFkAlunoId(fkAlunoId);
        this.setFkTurmaId(fkTurmaId);
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("O ID deve ser positivo.");
        this.id = id;
    }

    public int getFkAlunoId() { return fkAlunoId; }
    public void setFkAlunoId(int fkAlunoId) {
        if (fkAlunoId <= 0) throw new IllegalArgumentException("O ID do aluno deve ser positivo.");
        this.fkAlunoId = fkAlunoId;
    }

    public int getFkTurmaId() { return fkTurmaId; }
    public void setFkTurmaId(int fkTurmaId) {
        if (fkTurmaId <= 0) throw new IllegalArgumentException("O ID de turma deve ser positivo.");
        this.fkTurmaId = fkTurmaId;
    }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}