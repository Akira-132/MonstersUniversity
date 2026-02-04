package com.example.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;

    public Usuario(int id, String nome, String email, String telefone) {
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
    }

    public Usuario(String nome, String email, String telefone) {
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
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

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if (nome == null) {
            throw new NullPointerException("O nome não pode ser nulo.");
        }
        if (nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode estar em branco.");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        if (email == null) { // Exceção: verifica se o email é nulo
            throw new NullPointerException("O e-mail não pode ser nulo.");
        }
        validateEmail(email);
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        if (senha == null) { // Exceção: verifica se a senha é nula
            throw new NullPointerException("A senha não pode ser nula.");
        }
        if (senha.trim().isEmpty()) { // Exceção: verifica se a senha só contém espaço
            throw new IllegalArgumentException("A senha não pode estar em branco.");
        }
        validateSenha(senha);
        this.senha = senha;
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


    private static final Pattern PATTERN_EMAIL = Pattern.compile(
            "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
                    "(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,63}$"
    );
    private static final Pattern PATTERN_MINUSCULA = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_MAIUSCULA = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGITO = Pattern.compile("\\d");
    private static final Pattern PATTERN_ESPECIAL = Pattern.compile("[^A-Za-z0-9]");

    private void validateEmail(String email) {
        Matcher matcher = PATTERN_EMAIL.matcher(email);
        if (!matcher.matches()) { // Exceção: verifica se o e-mail tem formato válido.
            throw new IllegalArgumentException("O formato do e-mail é inválido: '" + email + "'.");
        }
    }

    private void validateSenha(String senha) {
        if (senha.length() < 8) { // Exceção: verifica se a senha tem no mínimo 8 caracteres
            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres.");
        }
        if (!PATTERN_MINUSCULA.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 1 letra minúscula.");
        }
        if (!PATTERN_MAIUSCULA.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 1 letra maiúscula.");
        }
        if (!PATTERN_DIGITO.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 1 dígito.");
        }
        if (!PATTERN_ESPECIAL.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 1 caractere especial.");
        }
    }

    private void validateTelefone(String telefoneLimpo) {
        int len = telefoneLimpo.length();
        if (len != 10 && len != 11) {
            throw new IllegalArgumentException("Telefone inválido. Deve conter 10 ou 11 dígitos (com DDD). Recebido: '" + telefoneLimpo + "'");
        }
    }
}
