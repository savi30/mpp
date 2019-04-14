package bookstore;

import bookstore.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
