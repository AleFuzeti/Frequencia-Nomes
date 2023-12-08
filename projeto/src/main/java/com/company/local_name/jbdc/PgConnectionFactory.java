package com.company.local_name.jbdc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class PgConnectionFactory extends ConnectionFactory {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public PgConnectionFactory() {

    }

    public void readProperties() throws IOException {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream("src/main/resources/application.properties");
            properties.load(inputStream);

            url = properties.getProperty("spring.datasource.url");
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
        } catch (IOException e) {
            System.err.println(e.getMessage());

            throw new IOException("Erro ao carregar arquivo de propriedades");
        }

    }
    @Bean
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