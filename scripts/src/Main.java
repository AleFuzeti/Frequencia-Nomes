import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {

    public static void main(String[] args) {
        try {
            // URL da API para obter as macrorregiões
            String regioesUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes";

            // Criação da URL e conexão para obter as macrorregiões
            URL regioesObj = new URL(regioesUrl);
            HttpURLConnection regioesCon = (HttpURLConnection) regioesObj.openConnection();
            regioesCon.setRequestMethod("GET");

            // Leitura da resposta da API para obter as macrorregiões
            BufferedReader regioesIn = new BufferedReader(new InputStreamReader(regioesCon.getInputStream()));
            StringBuilder regioesResponse = new StringBuilder();
            String regioesInputLine;

            while ((regioesInputLine = regioesIn.readLine()) != null) {
                regioesResponse.append(regioesInputLine);
            }

            regioesIn.close();

            // Configurar o ObjectMapper para a formatação com identação
            ObjectMapper prettyObjectMapper = new ObjectMapper();
            prettyObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Converter a resposta JSON em um array de objetos JSON
            JsonNode regioesJsonArray = prettyObjectMapper.readTree(regioesResponse.toString());

            // Caminho para a pasta de saída (uma pasta acima de src)
            String outputFolderPath = "../output";

            // Verificar se a pasta de saída existe e criá-la se não existir
            File outputFolder = new File(outputFolderPath);
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            // Salvar os dados das macrorregiões em um arquivo JSON com identação
            String regioesOutputPath = outputFolderPath + "/regioes.json";
            FileWriter regioesFileWriter = new FileWriter(regioesOutputPath);
            ObjectWriter regioesWriter = prettyObjectMapper.writerWithDefaultPrettyPrinter();
            regioesWriter.writeValue(regioesFileWriter, regioesJsonArray);
            regioesFileWriter.close();

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

            // Salvar os dados dos estados de todas as macrorregiões em um único arquivo JSON com identação
            String estadosOutputPath = outputFolderPath + "/estados_todas_regioes.json";
            FileWriter estadosFileWriter = new FileWriter(estadosOutputPath);
            ObjectWriter estadosWriter = prettyObjectMapper.writerWithDefaultPrettyPrinter();
            estadosWriter.writeValue(estadosFileWriter, prettyObjectMapper.readTree(estadosResponse.toString()));
            estadosFileWriter.close();

            System.out.println("Dados de todos os estados salvos em " + estadosOutputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}