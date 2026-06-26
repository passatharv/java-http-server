import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncLogger {

    private final BlockingQueue<String> logQueue =
            new LinkedBlockingQueue<>();

    public AsyncLogger() {

        Thread loggerThread = new Thread(() -> {

            while (true) {

                try {

                    String message =
                            logQueue.take();

                    try (PrintWriter writer =
                                 new PrintWriter(
                                         new FileWriter(
                                                 "logs/server.log",
                                                 true)))  {  //append meaning dont delete old 

                        writer.println(message);
                    }

                } catch (InterruptedException |
                         IOException e) {

                    e.printStackTrace();
                }
            }
        });

        loggerThread.setDaemon(true);
        loggerThread.start();
    }
 
    public void log(String message) {

        logQueue.offer(message);
    }
}