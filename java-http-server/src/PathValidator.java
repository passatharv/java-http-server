import java.nio.file.Path;

public class PathValidator {

    public static boolean isValidPath(String path) {

        Path normalizedPath =
                Path.of(path).normalize();

        return !normalizedPath.startsWith("..");
    }
}