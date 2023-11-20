import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
public class Main {

    public static void main(String[] args) {
        try {
            ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            String regioesUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes";
            String regioesResponse = HttpUtil.fetchDataFromAPI(regioesUrl);
            JsonNode regioesJsonArray = prettyObjectMapper.readTree(regioesResponse);

            String outputFolderPath = "../dados_brutos";
            FileUtil.createOutputFolder(outputFolderPath);

            String regioesOutputPath = outputFolderPath + "/regioes.json";
            FileUtil.writeJsonToFile(regioesOutputPath, prettyObjectMapper.writeValueAsString(regioesJsonArray));
            System.out.println("Dados das macrorregiões salvos em " + regioesOutputPath);

            // URL da API para obter os estados de todas as macrorregiões (IDs 1, 2, 3, 4, 5)
            String estadosUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes/1%7C2%7C3%7C4%7C5/estados";

            // Obter os estados de todas as macrorregiões
            String estadosResponse = HttpUtil.fetchDataFromAPI(estadosUrl);
            JsonNode estadosJsonArray = prettyObjectMapper.readTree(estadosResponse);

            // Salvar os dados dos estados de todas as macrorregiões em um único arquivo JSON com identação
            String estadosOutputPath = outputFolderPath + "/estados_todas_regioes.json";
            FileUtil.writeJsonToFile(estadosOutputPath, prettyObjectMapper.writeValueAsString(estadosJsonArray));
            System.out.println("Dados de todos os estados salvos em " + estadosOutputPath);

            // Criar uma lista de IDs de UFs a partir dos dados dos estados
            StringBuilder ufIds = new StringBuilder();
            for (JsonNode estado : estadosJsonArray) {
                String ufId = estado.get("id").asText();
                ufIds.append(ufId).append("|");
            }
            // Remover o último caractere "|" da lista
            if (ufIds.length() > 0) {
                ufIds.setLength(ufIds.length() - 1);
            }

            // URL da API para obter os municípios de todas as UFs
            String municipiosUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + ufIds + "/municipios";

            // Obter os municípios de todas as UFs
            String municipiosResponse = HttpUtil.fetchDataFromAPI(municipiosUrl);

            // Salvar os dados dos municípios de todas as UFs em um único arquivo JSON com identação
            String municipiosOutputPath = outputFolderPath + "/municipios_todas_ufs.json";
            FileUtil.writeJsonToFile(municipiosOutputPath, prettyObjectMapper.writeValueAsString(prettyObjectMapper.readTree(municipiosResponse)));
            System.out.println("Dados de todos os municípios salvos em " + municipiosOutputPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}