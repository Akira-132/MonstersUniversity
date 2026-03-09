package com.example.models;

public class Boletim {
    private int idAluno;
    private String nomeCompleto;
    private String disciplina;
    private Double mediaP1;
    private Double mediaP2;
    private Double mediaFinal;
    private String situacao;
    private int semestre;
    private int ano;

    public Boletim() {
    }

    public Boletim(int idAluno, String nomeCompleto, String disciplina, Double mediaP1, Double mediaP2,
                   Double mediaFinal, String situacao, int semestre, int ano) {
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
    public Double getMediaP1() { return mediaP1; }
    public Double getMediaP2() { return mediaP2; }
    public Double getMediaFinal() { return mediaFinal; }
    public String getSituacao() { return situacao; }
    public int getSemestre() { return semestre; }
    public int getAno() { return ano; }
}