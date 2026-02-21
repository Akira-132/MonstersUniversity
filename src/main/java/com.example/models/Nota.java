package com.example.models;

public class Nota {
    private int id;
    private String tipo;
    private int semestre;
    private int ano;
    private double nota;
    private int fkAlunoId;
    private int fkDisciplinaId;
    private Aluno aluno;
    private Disciplina disciplina;

    public Nota(String tipo, int semestre, int ano, double nota, int fkAlunoId, int fkDisciplinaId) {
        this.setTipo(tipo);
        this.setSemestre(semestre);
        this.setAno(ano);
        this.setNota(nota);
        this.setFkAlunoId(fkAlunoId);
        this.setFkDisciplinaId(fkDisciplinaId);
    }

    public Nota(int id, String tipo, int semestre, int ano, double nota, int fkAlunoId, int fkDisciplinaId) {
        this.setId(id);
        this.setTipo(tipo);
        this.setSemestre(semestre);
        this.setAno(ano);
        this.setNota(nota);
        this.setFkAlunoId(fkAlunoId);
        this.setFkDisciplinaId(fkDisciplinaId);
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        this.id = id;
    }

    public double getNota() { return nota; }
    public void setNota(double nota) {
        if (nota < 0 || nota > 10) throw new IllegalArgumentException("Nota deve ser entre 0 e 10");
        this.nota = Math.round(nota * 10.0) / 10.0;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) {
        if (tipo == null || (!tipo.equals("N1") && !tipo.equals("N2"))) {
            throw new IllegalArgumentException("Tipo deve ser N1 ou N2");
        }
        this.tipo = tipo;
    }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) {
        if (semestre < 1 || semestre > 2) throw new IllegalArgumentException("Semestre deve ser 1 ou 2");
        this.semestre = semestre;
    }

    public int getAno() { return ano; }
    public void setAno(int ano) {
        if (ano < 1900 || ano > 2100) throw new IllegalArgumentException("Ano inválido");
        this.ano = ano;
    }

    public int getFkAlunoId() { return fkAlunoId; }
    public void setFkAlunoId(int fkAlunoId) { this.fkAlunoId = fkAlunoId; }

    public int getFkDisciplinaId() { return fkDisciplinaId; }
    public void setFkDisciplinaId(int fkDisciplinaId) { this.fkDisciplinaId = fkDisciplinaId; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
}