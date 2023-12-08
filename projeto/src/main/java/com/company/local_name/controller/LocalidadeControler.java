package com.company.local_name.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocalidadeControler {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:8000/nome_do_banco";
        String user = "seu_usuario";
        String password = "sua_senha";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM nome_da_tabela";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Obtenha os dados da tabela aqui
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                // ...

                // Faça o que você precisa com os dados
                System.out.println("ID: " + id + ", Nome: " + nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
