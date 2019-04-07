package bookstore;


import bookstore.Service.ClientService;
import bookstore.tcp.TcpClient;
import bookstore.ui.Console;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pollos_hermanos.
 */
public class App {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());
        TcpClient tcpClient = new TcpClient(ClientService.SERVER_HOST,
                ClientService.SERVER_PORT);
        ClientService clientService =
                new ClientService(executorService, tcpClient);

        Console console = new Console(clientService);
        console.runConsole();

        executorService.shutdownNow();

        System.out.println("client - bye");
    }
}
