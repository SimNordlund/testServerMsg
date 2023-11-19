import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/storeQuestion", new StoreQuestionHandler());
        server.start();
    }
    //Stores a question in a textfile. Input from user is stored.
    static class StoreQuestionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String question = exchange.getRequestURI().getQuery();
            question = question.substring(question.indexOf("=") + 1);

            Files.write(Paths.get("src/question.txt"), (question + "\n").getBytes(), StandardOpenOption.APPEND);

            String response = "Question stored successfully!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
