import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) {
        try {
            // URL da API
            String url = "https://servicodados.ibge.gov.br/api/v1/localidades/regioes";

            // Criação da URL e conexão
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Configuração do método de requisição
            con.setRequestMethod("GET");

            // Leitura da resposta da API
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Converter a resposta JSON em um objeto Java
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonData = objectMapper.readValue(response.toString(), Object.class);

            // Converter o objeto JSON em uma string formatada com recuo
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);

            // Caminho para a pasta de saída (uma pasta acima de src)
            String outputPath = "../output/dados.json";

            // Verificar se a pasta de saída existe e criá-la se não existir
            File outputFolder = new File("../output");
            if (!outputFolder.exists()) {
                outputFolder.mkdirs(); // Cria a pasta de saída
            }

            // Salvar os dados em um arquivo JSON na pasta de saída
            FileWriter fileWriter = new FileWriter(outputPath);
            fileWriter.write(jsonString);
            fileWriter.close();

            // Exibe a resposta da API
            System.out.println("Dados salvos em " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
