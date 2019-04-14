package bookstore;


import bookstore.config.ApplicationConfig;
import bookstore.service.ClientServerService;
import bookstore.service.ClientService;
import bookstore.ui.Console;
import bookstore.utils.service.BookService;
import bookstore.utils.service.ReportService;
import bookstore.utils.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService userService = context.getBean(UserService.class);
        ReportService reportService = context.getBean(ReportService.class);
        BookService bookService = context.getBean(BookService.class);

        ClientServerService service = new ClientService(bookService, userService, reportService);

        Console console = new Console(service);
        console.runConsole();

        executorService.shutdownNow();

        System.out.println("client - bye");

        System.out.println("client - bye");
    }
}
