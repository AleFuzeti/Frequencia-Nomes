import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        try {
            // Adicione no início do método main
            List<JsonNode> batchData = new ArrayList<>();

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

            // Ler dados dos municípios de todas as UFs
            JsonNode municipiosJsonArray = prettyObjectMapper.readTree(municipiosResponse);

            // Lógica para iterar sobre os municípios e fazer solicitações em lotes
            int batchSize = 100;
            int totalMunicipios = municipiosJsonArray.size();
            int batches = (int) Math.ceil((double) totalMunicipios / batchSize);

            batchData.clear();
            for (int i = 0; i < 5570; i++) {
                JsonNode municipio = municipiosJsonArray.get(i);
                int municipioId = municipio.get("id").asInt();

                // URL para a solicitação GET do município atual
                String municipioUrl = "https://servicodados.ibge.gov.br/api/v2/censos/nomes/ranking?localidade=" + municipioId;

                boolean dadosObtidos = false;
                int tentativas = 0;

                while (!dadosObtidos && tentativas < 1000) { // Limite de 100 tentativas
                    try {
                        // Lógica para fazer a solicitação GET com o ID do município atual
                        String municipioResponse = HttpUtil.fetchDataFromAPI(municipioUrl);

                        // Salvar os dados do município em um arquivo JSON com identação
                        String municipioOutputPath = outputFolderPath + "/ranking/municipio_" + municipioId + ".json";
                        FileUtil.writeJsonToFile(municipioOutputPath, prettyObjectMapper.writeValueAsString(prettyObjectMapper.readTree(municipioResponse)));
                        System.out.println("Dados do município " + i + " salvos em " + municipioOutputPath);

                        dadosObtidos = true; // Indica que os dados foram obtidos com sucesso
                    } catch (IOException e) {
                        tentativas++;
                        System.err.println("Erro ao obter dados para o município " + municipioId + ". Tentativa " + tentativas + ": " + e.getMessage());
                        // Adicione lógica de tratamento de erro aqui, se necessário.
                    }
                }

                if (!dadosObtidos) {
                    System.err.println("Não foi possível obter dados para o município " + municipioId + " após 3 tentativas.");
                    // Adicione lógica de tratamento adicional, se necessário.
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}