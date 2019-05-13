package bookstore.web;

import bookstore.web.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String... args) {
        SpringApplication.run(AppConfig.class, args);
    }
}
