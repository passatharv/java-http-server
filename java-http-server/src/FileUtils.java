import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static byte[] readFile(String filePath)
            throws IOException {

        return Files.readAllBytes(
                Path.of(filePath)
        );
    }
}