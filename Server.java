import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
    private static int contador = 0;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Rota para o HTML
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String html = new String(Files.readAllBytes(Paths.get("index.html")));
                exchange.sendResponseHeaders(200, html.length());
                OutputStream os = exchange.getResponseBody();
                os.write(html.getBytes());
                os.close();
            }
        });

        // Rota da API (contador)
        server.createContext("/api/contador", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                contador++;
                String response = "{\"visitas\": " + contador + "}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("🚀 Servidor rodando em http://localhost:8080");
        System.out.println("📊 Contador em /api/contador");
    }
}
