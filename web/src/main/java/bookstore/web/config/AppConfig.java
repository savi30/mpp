package bookstore.web.config;

import bookstore.core.config.ApplicationConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("bookstore.core")
@Import({ApplicationConfig.class})
@PropertySources({@PropertySource(value = "classpath:db.properties")})
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
