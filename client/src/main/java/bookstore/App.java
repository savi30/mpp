package bookstore;

import bookstore.config.ApplicationConfig;
import bookstore.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

/**
 * @author pollos_hermanos.
 */

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Console console = (Console) context.getBean("console");
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        console.runConsole();
    }
}
