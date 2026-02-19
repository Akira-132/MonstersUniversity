package com.example.controllers;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        try {
            Class.forName("org.postgresql.Driver");

            Dotenv dotenv = Dotenv.load();
            DB_URL = dotenv.get("DB_URL");
            DB_USER = dotenv.get("DB_USER");
            DB_PASSWORD = dotenv.get("DB_PASSWORD");

            if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
                throw new NullPointerException("Variáveis de ambiente (DB_URL, DB_USER, DB_PASSWORD) não encontradas no .env.");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Erro Crítico: Driver JDBC PostgreSQL não encontrado no classpath.");
            throw new ExceptionInInitializerError(e);

        } catch (DotenvException | NullPointerException e) {
            System.err.println("Erro ao carregar configurações do .env: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    public void desconectar(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
