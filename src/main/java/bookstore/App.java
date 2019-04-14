package bookstore;

import bookstore.config.ApplicationConfig;
import bookstore.service.user.UserService;
import bookstore.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author pollos_hermanos.
 */

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        Console console = (Console) context.getBean("console");
        console.runConsole();
    }
}
