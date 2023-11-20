import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static void createOutputFolder(String folderPath) {
        File outputFolder = new File(folderPath);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
    }

    public static void writeJsonToFile(String filePath, String jsonData) throws IOException {
        File outputFile = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(jsonData);
        }
    }
}