package com.company.local_name.controller;

import com.company.local_name.controller.utils.HttpUtil;
import com.company.local_name.DAO.impl.PgRegiaoDAO;
import com.company.local_name.DAO.impl.PgEstadoDAO;
import com.company.local_name.DAO.impl.PgCidadeDAO;
import com.company.local_name.model.Cidade;
import com.company.local_name.model.Estado;
import com.company.local_name.model.Regiao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/localidades")
@Component
public class LocalidadeControler {

    // private PgRegiaoDAO regiaoDAO;
    // private PgEstadoDAO estadoDAO;
    // private PgCidadeDAO cidadeDAO;

    // @Autowired
    // public LocalidadeControler(PgRegiaoDAO regiaoDAO, PgEstadoDAO estadoDAO, PgCidadeDAO cidadeDAO) {
    //     this.regiaoDAO = regiaoDAO;
    //     this.estadoDAO = estadoDAO;
    //     this.cidadeDAO = cidadeDAO;
    // }

    @GetMapping("/regioes")
    public List<Regiao> getRegioes() {
        List<Regiao> regioes = new ArrayList<>();

        String url = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
        String user = "matheus";
        String password = "bd23";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM local_names_db.regiao";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Regiao regiao = new Regiao(
                        resultSet.getString("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("sigla")
                );
                regioes.add(regiao);
            } else {
                System.out.println("No region found");
                populateRegioes(regioes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regioes;
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
                        resultSet.getString("sigla_reg")
                );
                estados.add(estado);
            } else {
                System.out.println("No State found");
                populateStates(estados);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estados;
    }

    @GetMapping("/cidades")
    public List<Cidade> getCidades() {
        List<Cidade> cidades = new ArrayList<>();

        String url = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
        String user = "matheus";
        String password = "bd23";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM local_names_db.cidade";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Cidade cidade = new Cidade(
                        resultSet.getString("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("sigla_estado")
                );
                cidades.add(cidade);
            } else {
                System.out.println("No city found");
                populateCities(cidades);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return cidades;
    }

    public void populateRegioes(List<Regiao> regioes) {
        ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            String regioesUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes";
            String regioesResponse = HttpUtil.fetchDataFromAPI(regioesUrl);
            JsonNode regioesJsonArray = prettyObjectMapper.readTree(regioesResponse);
            System.out.println("Dados das macrorregiões: " + regioesJsonArray);

            // inserir no banco de dados
            for (JsonNode regiao : regioesJsonArray) {
                String id = regiao.get("id").asText();
                String nome = regiao.get("nome").asText();
                String sigla = regiao.get("sigla").asText();

                String url = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
                String user = "matheus";
                String password = "bd23";
                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    String sql = "INSERT INTO local_names_db.regiao (id, sigla, nome) VALUES (?, ?, ?)";

                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, id);
                        statement.setString(2, sigla);
                        statement.setString(3, nome);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Regiao regiaoo = new Regiao(id, nome, sigla);
                //regiaoDAO.create(regiaoo);
                regioes.add(regiaoo);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void populateStates(List<Estado> estados){
        ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            String estadosUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes/1%7C2%7C3%7C4%7C5/estados";
            String estadosResponse = HttpUtil.fetchDataFromAPI(estadosUrl);
            JsonNode estadosJsonArray = prettyObjectMapper.readTree(estadosResponse);
            System.out.println("Dados dos estados: " + estadosJsonArray);

            // tratar dados e inserir no banco de dados
            for (JsonNode estado : estadosJsonArray) {
                String id = estado.get("id").asText();
                String nome = estado.get("nome").asText();
                String sigla = estado.get("sigla").asText();
                String sigla_reg = estado.get("regiao").get("sigla").asText();

                String url = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
                String user = "matheus";
                String password = "bd23";
                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    String sql = "INSERT INTO local_names_db.estado (id, nome, sigla, sigla_reg) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, id);
                        statement.setString(2, nome);
                        statement.setString(3, sigla);
                        statement.setString(4, sigla_reg);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Estado estadoo = new Estado(id, nome, sigla, sigla_reg);
                //estadoDAO.create(estadoo);
                estados.add(estadoo);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void populateCities(List<Cidade> cidades){
        ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            String url = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
            String user = "matheus";
            String password = "bd23";

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 Statement statement = connection.createStatement()) {

                String query = "SELECT * FROM local_names_db.estado";
                ResultSet resultSet = statement.executeQuery(query);

                String ufIds = "";
                while (resultSet.next()) {
                    String ufId = resultSet.getString("id");
                    ufIds += ufId + "|";
                }
                // Remover o último caractere "|" da lista
                if (ufIds.length() > 0) {
                    ufIds = ufIds.substring(0, ufIds.length() - 1);
                }

                String municipiosUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + ufIds + "/municipios";
                String municipiosResponse = HttpUtil.fetchDataFromAPI(municipiosUrl);
                JsonNode municipiosJsonArray = prettyObjectMapper.readTree(municipiosResponse);
                System.out.println("Dados dos municípios: " + municipiosJsonArray);

                // tratar dados e inserir no banco de dados
                for (JsonNode municipio : municipiosJsonArray) {
                    String id = municipio.get("id").asText();
                    String nome = municipio.get("nome").asText();
                    String sigla_estado = municipio.get("microrregiao").get("mesorregiao").get("UF").get("sigla").asText();

                    String sql = "INSERT INTO local_names_db.cidade (id, nome, sigla_estado) VALUES (?, ?, ?)";
                    try (PreparedStatement statement2 = connection.prepareStatement(sql)) {
                        statement2.setString(1, id);
                        statement2.setString(2, nome);
                        statement2.setString(3, sigla_estado);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Cidade cidade = new Cidade(id, nome, sigla_estado);
                    //cidadeDAO.create(cidade);
                    cidades.add(cidade);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }       
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
