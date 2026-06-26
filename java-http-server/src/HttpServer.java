import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final int port;
    private final ExecutorService threadPool;
    private final LRUCache<String, byte[]> cache;
    private final StatsManager statsManager;
    private final AsyncLogger logger;

    public HttpServer(int port, int poolSize) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(poolSize);
        this.cache = new LRUCache<>(100);
        this.statsManager = new StatsManager();
        this.logger = new AsyncLogger();
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("================================");
            System.out.println(" Java HTTP Server Started");
            System.out.println(" http://localhost:" + port);
            System.out.println("================================");

            while (true) {

                Socket clientSocket = serverSocket.accept();

                threadPool.execute(
                        new ClientHandler(
                                clientSocket,
                                cache,
                                statsManager,
                                logger
                        )
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}