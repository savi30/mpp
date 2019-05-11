package bookstore.client;

import bookstore.client.config.ClientConfig;
import bookstore.client.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author pollos_hermanos.
 */

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
        Console console = (Console) context.getBean("console");
        console.runConsole();
    }
}
