package com.company.local_name.jbdc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PgConnectionFactory extends ConnectionFactory {

    private String url;
    private String username;
    private String password;

    public PgConnectionFactory() {

    }

    public void readProperties() throws IOException {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream("application.properties");
            properties.load(inputStream);

            url = properties.getProperty("spring.datasource.url");
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
        } catch (IOException e) {
            System.err.println(e.getMessage());

            throw new IOException("Erro ao carregar arquivo de propriedades");
        }

    }

    @Override
    public Connection getConnection() throws IOException, SQLException, ClassNotFoundException {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            readProperties();

            connection = java.sql.DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            throw new ClassNotFoundException("Driver não encontrado");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new SQLException("Erro ao conectar com o banco de dados");
        }

        return connection;

    }



}