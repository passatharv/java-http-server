import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final LRUCache<String, byte[]> cache;
    private final StatsManager statsManager;
    private final AsyncLogger logger;

    public ClientHandler(Socket clientSocket, LRUCache<String, byte[]> cache, StatsManager statsManager, AsyncLogger logger) {
        this.clientSocket = clientSocket;
        this.cache = cache;
        this.statsManager = statsManager;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );

            String requestLine = reader.readLine();
            if(requestLine == null){
                return;
            }

            String[] parts = requestLine.split(" ");

            HttpRequest request = new HttpRequest(
                parts[0],
                parts[1],
                parts[2]
            );

            logger.log(
                "[" + java.time.LocalDateTime.now() + "] "
                + request.getMethod()
                + " "
                + request.getPath()
                + " "
                + request.getVersion()
            );

            statsManager.incrementRequests();

            if (!PathValidator.isValidPath(request.getPath())) {
                throw new IOException("Invalid Path");
            }

            OutputStream out = clientSocket.getOutputStream();

            if (request.getPath().equals("/stats")) {
                handleStatsRequest(out);
                return;
            }

            String filePath = request.getPath().equals("/")
                ? "../www/index.html"
                : "../www" + request.getPath();

            byte[] bodyBytes = cache.get(filePath);

            if (bodyBytes == null) {
                statsManager.incrementCacheMisses();
                bodyBytes = FileUtils.readFile(filePath);
                cache.put(filePath, bodyBytes);
            } else {
                statsManager.incrementCacheHits();
            }
            // byte[] bodyBytes = FileUtils.readFile(filePath);

            String extension = "";
            int dotIndex = filePath.lastIndexOf('.');
            if (dotIndex != -1) {
                extension = filePath.substring(dotIndex + 1);
            }

            String contentType = MimeTypes.getMimeType(extension);

            sendResponse(out, bodyBytes, contentType);

        } catch (IOException e) {
            try {
                send404Response(clientSocket.getOutputStream());
            } catch (IOException ignored) {
            }
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void handleStatsRequest(OutputStream out) throws IOException {
        String body = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Server Statistics</title>
                </head>
                <body>
                    <h1>Java HTTP Server Statistics</h1>
                    <p>Total Requests: """ + statsManager.getTotalRequests() + """
                    </p>
                    <p>Cache Hits: """ + statsManager.getCacheHits() + """
                    </p>
                    <p>Cache Misses: """ + statsManager.getCacheMisses() + """
                    </p>
                </body>
                </html>
                """;

        sendResponse(out, body.getBytes(StandardCharsets.UTF_8), "text/html");
    }

    private void send404Response(OutputStream out) throws IOException {
        String body = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>404 Not Found</title>
                </head>
                <body>
                    <h1>404 Not Found</h1>
                    <p>The page you requested does not exist.</p>
                </body>
                </html>
                """;

        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

        String headers =
            "HTTP/1.1 404 Not Found\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "Content-Length: " + bodyBytes.length + "\r\n" +
            "Connection: close\r\n" +
            "\r\n";

        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(bodyBytes);
        out.flush();
    }

    private void sendResponse(OutputStream out, byte[] bodyBytes, String contentType) throws IOException {
        String headers =
            "HTTP/1.1 200 OK\r\n" +
            "Content-Type: " + contentType + "; charset=UTF-8\r\n" +
            "Content-Length: " + bodyBytes.length + "\r\n" +
            "Connection: close\r\n" +
            "\r\n";

        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(bodyBytes);
        out.flush();
    }
}