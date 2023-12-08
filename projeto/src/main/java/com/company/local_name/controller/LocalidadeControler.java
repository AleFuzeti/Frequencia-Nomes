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

     @Autowired
     private PgEstadoDAO estadoDAO;
     @Autowired
     private PgRegiaoDAO regiaoDAO;
     @Autowired
     private PgCidadeDAO cidadeDAO;
     @Autowired
     public LocalidadeControler(PgRegiaoDAO regiaoDAO, PgEstadoDAO estadoDAO, PgCidadeDAO cidadeDAO) {
         this.regiaoDAO = regiaoDAO;
         this.estadoDAO = estadoDAO;
         this.cidadeDAO = cidadeDAO;
     }

    private static final String DB_URL = "jdbc:postgresql://sicm.dc.uel.br:5432/matheus";
    private static final String DB_USER = "matheus";
    private static final String DB_PASSWORD = "bd23";

    @GetMapping("/regioes")
    public List<Regiao> getRegioes() {
        List<Regiao> regioes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM local_names_db.regiao";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Regiao regiao = new Regiao(
                        resultSet.getString("id"),
                        resultSet.getString("sigla"),
                        resultSet.getString("nome")
                );
                regioes.add(regiao);
            }
    
            if (regioes.isEmpty()) {
                System.out.println("No regions found");
                regioes = populateRegioes(regioes);
            }
            return regioes;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regioes;
    }

    @GetMapping("/estados")
    public List<Estado> getEstados() {
        List<Estado> estados = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM local_names_db.estado";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                Estado estado = new Estado(
                        resultSet.getString("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("sigla"),
                        resultSet.getString("sigla_reg")
                );
                estados.add(estado);
            } 
            if (estados.isEmpty()) {
                System.out.println("No states found");
                estados = populateStates(estados);
            }
            return estados;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estados;
    }

    @GetMapping("/cidades")
    public List<Cidade> getCidades(){
        List<Cidade> cidades = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM local_names_db.cidade";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                Cidade cidade = new Cidade(
                        resultSet.getString("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("sigla_estado")
                );
                cidades.add(cidade);
            }
            if (cidades.isEmpty()) {
                System.out.println("No cities found");
                cidades = populateCities(cidades);
            }
            return cidades;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cidades;
    }

    public List<Regiao> populateRegioes(List<Regiao> regioes) {
        ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            String regioesUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes";
            String regioesResponse = HttpUtil.fetchDataFromAPI(regioesUrl);
            JsonNode regioesJsonArray = prettyObjectMapper.readTree(regioesResponse);
            System.out.println("Dados das regiões corretos!! Tentativa de conectar ao banco.....");

            // inserir no banco de dados
            for (JsonNode regiao : regioesJsonArray) {
                String id = regiao.get("id").asText();
                String sigla = regiao.get("sigla").asText();
                String nome = regiao.get("nome").asText();

                Regiao regiaoo = new Regiao(id, sigla, nome);
                regiaoDAO.create(regiaoo);
                regioes.add(regiaoo);
            }        
            return regioes;
        }
        catch(Exception e){
            System.out.println("Erro ao buscar api de região");
            e.printStackTrace();
        }
        return regioes;
    }

    public List<Estado> populateStates(List<Estado> estados){
        ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            String estadosUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes/1%7C2%7C3%7C4%7C5/estados";
            String estadosResponse = HttpUtil.fetchDataFromAPI(estadosUrl);
            JsonNode estadosJsonArray = prettyObjectMapper.readTree(estadosResponse);
            System.out.println("Dados dos estados corretos!! Tentativa de conectar ao banco.....");

            // tratar e inserir no banco de dados
            for (JsonNode estado : estadosJsonArray) {
                String id = estado.get("id").asText();
                String nome = estado.get("nome").asText();
                String sigla = estado.get("sigla").asText();
                String siglaReg = estado.get("regiao").get("sigla").asText();

                Estado estadoo = new Estado(id, nome, sigla, siglaReg);
                estadoDAO.create(estadoo);
                estados.add(estadoo);
            }
            return estados;
        }
        catch(Exception e){
            System.out.println("Erro ao buscar api de estados");
            e.printStackTrace();
        }
        return estados;
    }

    public List<Cidade> populateCities(List<Cidade> cidades){
        ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM local_names_db.estado";
                ResultSet resultSet = statement.executeQuery(query);
                    
                String ufIds = "";
                while (resultSet.next()) {
                    ufIds += resultSet.getString("id") + "|";
                }
                // Remover o último caractere "|" da lista
                if (ufIds.length() > 0) {
                    ufIds = ufIds.substring(0, ufIds.length() - 1);
                }

                String municipiosUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + ufIds + "/municipios";
                String municipiosResponse = HttpUtil.fetchDataFromAPI(municipiosUrl);
                JsonNode municipiosJsonArray = prettyObjectMapper.readTree(municipiosResponse);
                //System.out.println("Dados dos municípios corretos!! Tentativa de conectar ao banco.....");

                // tratar e inserir no banco de dados
                for (JsonNode municipio : municipiosJsonArray) {
                    String id = municipio.get("id").asText();
                    String nome = municipio.get("nome").asText();
                    String siglaEstado = municipio.get("microrregiao").get("mesorregiao").get("UF").get("sigla").asText();

                    Cidade cidade = new Cidade(id, nome, siglaEstado);
                    cidadeDAO.create(cidade);
                    cidades.add(cidade);
                }
                return cidades;
            }
            catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados");
                e.printStackTrace();
            }       
        }
        catch(Exception e){
            System.out.println("Erro ao buscar api de municípios");
            e.printStackTrace();
        }
        return cidades;
    }

}
