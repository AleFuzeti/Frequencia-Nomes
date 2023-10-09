import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.*;

public class Main {

    // Função para fazer a solicitação HTTP e retornar a resposta como uma String
    private static String fetchDataFromAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }

    public static void main(String[] args) {
        try {
            // Configurar o ObjectMapper para a formatação com identação
            ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            // URL da API para obter as macrorregiões
            String regioesUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes";
            String regioesResponse = fetchDataFromAPI(regioesUrl);
            JsonNode regioesJsonArray = prettyObjectMapper.readTree(regioesResponse);

            // Caminho para a pasta de saída (uma pasta acima de src)
            String outputFolderPath = "./output";
            File outputFolder = new File(outputFolderPath);
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            // Salvar os dados das macrorregiões em um arquivo JSON com identação
            String regioesOutputPath = outputFolderPath + "/regioes.json";
            try (FileWriter regioesFileWriter = new FileWriter(regioesOutputPath)) {
                prettyObjectMapper.writeValue(regioesFileWriter, regioesJsonArray);
            }
            System.out.println("Dados das macrorregiões salvos em " + regioesOutputPath);

            // URL da API para obter os estados de todas as macrorregiões (IDs 1, 2, 3, 4, 5)
            String estadosUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes/1%7C2%7C3%7C4%7C5/estados";

            // Criação da URL e conexão para obter os estados de todas as macrorregiões
            URL estadosObj = new URL(estadosUrl);
            HttpURLConnection estadosCon = (HttpURLConnection) estadosObj.openConnection();
            estadosCon.setRequestMethod("GET");

            // Leitura da resposta da API para obter os estados de todas as macrorregiões
            BufferedReader estadosIn = new BufferedReader(new InputStreamReader(estadosCon.getInputStream()));
            StringBuilder estadosResponse = new StringBuilder();
            String estadosInputLine;

            while ((estadosInputLine = estadosIn.readLine()) != null) {
                estadosResponse.append(estadosInputLine);
            }

            estadosIn.close();

            // Configurar o ObjectMapper para a formatação com identação
            prettyObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Converter a resposta JSON em um array de objetos JSON
            JsonNode estadosJsonArray = prettyObjectMapper.readTree(estadosResponse.toString());

            // Salvar os dados dos estados de todas as macrorregiões em um único arquivo JSON com identação
            String estadosOutputPath = outputFolderPath + "/estados_todas_regioes.json";
            FileWriter estadosFileWriter = new FileWriter(estadosOutputPath);
            ObjectWriter estadosWriter = prettyObjectMapper.writerWithDefaultPrettyPrinter();
            estadosWriter.writeValue(estadosFileWriter, estadosJsonArray);
            estadosFileWriter.close();

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

            // Criação da URL e conexão para obter os municípios de todas as UFs
            URL municipiosObj = new URL(municipiosUrl);
            HttpURLConnection municipiosCon = (HttpURLConnection) municipiosObj.openConnection();
            municipiosCon.setRequestMethod("GET");

            // Leitura da resposta da API para obter os municípios de todas as UFs
            BufferedReader municipiosIn = new BufferedReader(new InputStreamReader(municipiosCon.getInputStream()));
            StringBuilder municipiosResponse = new StringBuilder();
            String municipiosInputLine;

            while ((municipiosInputLine = municipiosIn.readLine()) != null) {
                municipiosResponse.append(municipiosInputLine);
            }

            municipiosIn.close();

            // Salvar os dados dos municípios de todas as UFs em um único arquivo JSON com identação
            String municipiosOutputPath = outputFolderPath + "/municipios_todas_ufs.json";
            FileWriter municipiosFileWriter = new FileWriter(municipiosOutputPath);
            ObjectWriter municipiosWriter = prettyObjectMapper.writerWithDefaultPrettyPrinter();
            municipiosWriter.writeValue(municipiosFileWriter, prettyObjectMapper.readTree(municipiosResponse.toString()));
            municipiosFileWriter.close();

            System.out.println("Dados de todos os municípios salvos em " + municipiosOutputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
