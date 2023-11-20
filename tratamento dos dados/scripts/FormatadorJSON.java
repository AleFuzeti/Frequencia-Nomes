import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class FormatadorJSON {

    public static void main(String[] args) {
        String caminhoEntrada = "../dados_brutos/estados_todas_regioes.json";
        String caminhoSaida = "../dados_tratados/estados_formatados.json";

        try {
            // Carregar o arquivo JSON de entrada
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode dadosBrutos = objectMapper.readTree(new File(caminhoEntrada));

            // Formatar os dados
            JsonNode dadosFormatados = formatarDados(dadosBrutos);

            // Salvar dados formatados no arquivo de saída
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(caminhoSaida), dadosFormatados);

            System.out.println("Dados formatados e salvos com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonNode formatarDados(JsonNode dadosBrutos) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Array para armazenar os dados formatados
        objectMapper.createArrayNode();

        // Iterar sobre os estados
        Iterator<JsonNode> estadosIterator = dadosBrutos.elements();
        while (estadosIterator.hasNext()) {
            JsonNode estado = estadosIterator.next();
            // Formatar os dados do estado
            String siglaRegiao = estado.path("regiao").path("sigla").asText();
            ((com.fasterxml.jackson.databind.node.ObjectNode) estado).put("sigla_regiao", siglaRegiao);
        }

        return dadosBrutos;
    }
}