package com.company.local_name.controller;

import com.company.local_name.DAO.impl.PgRegiaoDAO;
import com.company.local_name.DAO.impl.PgEstadoDAO;
import com.company.local_name.DAO.impl.PgCidadeDAO;
import com.company.local_name.model.Estado;
import com.company.local_name.model.Regiao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/localidades")
public class LocalidadeControler {

    private final PgRegiaoDAO regiaoDAO;
    private final PgEstadoDAO estadoDAO;
    private final PgCidadeDAO cidadeDAO;

    @Autowired
    public LocalidadeControler(PgRegiaoDAO regiaoDAO, PgEstadoDAO estadoDAO, PgCidadeDAO cidadeDAO) {
        this.regiaoDAO = regiaoDAO;
        this.estadoDAO = estadoDAO;
        this.cidadeDAO = cidadeDAO;
    }

    @GetMapping("/estados")
    public List<Estado> getEstados() {
        List<Estado> estados = new ArrayList<>();

        String url = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
        String user = "matheus";
        String password = "bd23";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM local_names_db.estado";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Estado estado = new Estado(
                        resultSet.getString("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("sigla"),
                        resultSet.getString("id_regiao")
                );
                estados.add(estado);
            } else {
                System.out.println("No data found");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estados;
    }
}
