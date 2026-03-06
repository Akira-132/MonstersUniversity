package com.example.models;

public class Boletim {
    private int idAluno;
    private String nomeCompleto;
    private String disciplina;
    private double mediaP1;
    private double mediaP2;
    private double mediaFinal;
    private String situacao;
    private int semestre;
    private int ano;

    public Boletim() {
    }

    public Boletim(int idAluno, String nomeCompleto, String disciplina, double mediaP1, double mediaP2, double mediaFinal, String situacao, int semestre, int ano) {
        this.idAluno = idAluno;
        this.nomeCompleto = nomeCompleto;
        this.disciplina = disciplina;
        this.mediaP1 = mediaP1;
        this.mediaP2 = mediaP2;
        this.mediaFinal = mediaFinal;
        this.situacao = situacao;
        this.semestre = semestre;
        this.ano = ano;
    }

    public int getIdAluno() { return idAluno; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getDisciplina() { return disciplina; }
    public double getMediaP1() { return mediaP1; }
    public double getMediaP2() { return mediaP2; }
    public double getMediaFinal() { return mediaFinal; }
    public String getSituacao() { return situacao; }
    public int getSemestre() { return semestre; }
    public int getAno() { return ano; }
}