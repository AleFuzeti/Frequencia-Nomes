package com.company.local_name.controller;

import com.company.local_name.DAO.impl.PgNomeDAO;
import com.company.local_name.controller.utils.HttpUtil;
import com.company.local_name.model.Nome;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/nomes")
@Component
public class NomesController {

     @Autowired
     private final PgNomeDAO nomeDAO;

     @Autowired
     public NomesController(PgNomeDAO nomeDAO) {
         this.nomeDAO = nomeDAO;
     }

    private static final String DB_URL = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
    private static final String DB_USER = "matheus";
    private static final String DB_PASSWORD = "bd23";

    @GetMapping("/nomes")
    public List<Nome> getNomes(@RequestParam String localidade) {
        List<Nome> nomes = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // Verificar se existem dados para a localidade fornecida
            String countQuery = "SELECT COUNT(*) AS count FROM local_names_db.nome WHERE localidade = ?";
            try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
                countStatement.setString(1, localidade);
                ResultSet countResultSet = countStatement.executeQuery();

                if (countResultSet.next() && countResultSet.getInt("count") > 0) {
                    // Dados existem, então agora podemos buscar os nomes
                    String query = "SELECT * FROM local_names_db.nome WHERE localidade = ?";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, localidade);
                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                            Nome nome = new Nome(
                                    resultSet.getString("localidade"),
                                    resultSet.getString("nome"),
                                    resultSet.getInt("frequencia"),
                                    resultSet.getInt("ranking")
                            );
                            nomes.add(nome);
                        }
                    }
                } else {
                    // Nenhum dado encontrado para a localidade
                    System.out.println("No data found for the provided localidade: " + localidade);
                    nomes = ranking_nomes(nomes, localidade);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomes;
    }

    public List<Nome> ranking_nomes(List<Nome> nomes, String localidade) {
        ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            String nomesUrl = "https://servicodados.ibge.gov.br/api/v2/censos/nomes/ranking?localidade=" + localidade;
            String nomesResponse = HttpUtil.fetchDataFromAPI(nomesUrl);
            JsonNode nomesJsonArray = prettyObjectMapper.readTree(nomesResponse);
            System.out.println("Dados dos nomes corretos!! Tentativa de conectar ao banco.....");
            System.out.println("Dados: " + nomesJsonArray);
            // inserir no banco de dados
            for (JsonNode name : nomesJsonArray) {
                JsonNode resArray = name.get("res");

                if (resArray != null && resArray.isArray()) {
                    for (JsonNode nomeNode : resArray) {
                        String nome = nomeNode.get("nome").asText();
                        int frequencia = nomeNode.get("frequencia").asInt();
                        int rank = nomeNode.get("ranking").asInt();

                        Nome nomee = new Nome(localidade, nome, frequencia, rank);
                        nomeDAO.create(nomee);
                        nomes.add(nomee);
                    }
                } else {
                    System.out.println("Array 'res' vazio ou inexistente para o objeto: " + name);
                }
            }
            return nomes;
        }
        catch(Exception e){
            System.out.println("Erro ao buscar api de nomes");
            e.printStackTrace();
        }

        return nomes;
    }
}
