public class Main {

    public static void main(String[] args) {

        HttpServer server = new HttpServer(8080, 10);
        server.start();

    }
}