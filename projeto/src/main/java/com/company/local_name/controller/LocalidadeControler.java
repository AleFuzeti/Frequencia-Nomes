package com.company.local_name.controller;
import com.company.local_name.DAO.impl.PgRegiaoDAO;
import com.company.local_name.DAO.impl.PgEstadoDAO;
import com.company.local_name.DAO.impl.PgCidadeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocalidadeControler {
    private final PgRegiaoDAO regiaoDAO;
    private final PgEstadoDAO estadoDAO;
    private final PgCidadeDAO cidadeDAO;

    public LocalidadeControler(PgRegiaoDAO regiaoDAO, PgEstadoDAO estadoDAO, PgCidadeDAO cidadeDAO) {
        this.regiaoDAO = regiaoDAO;
        this.estadoDAO = estadoDAO;
        this.cidadeDAO = cidadeDAO;
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
        String user = "matheus";
        String password = "bd23";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM local_names_db.regiao";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                // Se o conjunto de resultados está vazio
                System.out.println("A tabela está vazia.");
            } else {
                // Se há dados na tabela
                do {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");

                    // Faça o que você precisa com os dados
                    System.out.printf("ID: %-5d Nome: %-20s%n", id, nome);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
