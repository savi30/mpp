package bookstore.web.config;

import bookstore.web.converters.BookConverter;
import bookstore.web.converters.BookDtoConverter;
import bookstore.web.converters.LogEntryConverter;
import bookstore.web.converters.LogEntryDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("bookstore")
public class AppConfig {
    @Autowired
    ApplicationContext context;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8080")
                        .allowedMethods("GET", "POST", "DELETE", "PUT");
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new BookConverter());
                registry.addConverter(new BookDtoConverter());
                registry.addConverter(new LogEntryConverter());
                registry.addConverter(context.getBean(LogEntryDtoConverter.class));
            }
        };
    }
}
