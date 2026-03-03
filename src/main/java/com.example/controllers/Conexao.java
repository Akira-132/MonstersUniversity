package com.example.controllers;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (isNullOrEmpty(url)) {
            url = dotenv.get("DB_URL");
        }
        if (isNullOrEmpty(user)) {
            user = dotenv.get("DB_USER");
        }
        if (isNullOrEmpty(password)) {
            password = dotenv.get("DB_PASSWORD");
        }

        DB_URL = url;
        DB_USER = user;
        DB_PASSWORD = password;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro crítico: driver JDBC PostgreSQL não encontrado no classpath.");
        }

    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public Connection conectar() throws SQLException {
        if (isNullOrEmpty(DB_URL) || isNullOrEmpty(DB_USER) || isNullOrEmpty(DB_PASSWORD)) {
            throw new SQLException("Configurações do banco ausentes. Defina as variáveis de ambiente DB_URL, DB_USER e DB_PASSWORD ou coloque um .env no classpath (src/main/resources).");
        }

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void desconectar(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}