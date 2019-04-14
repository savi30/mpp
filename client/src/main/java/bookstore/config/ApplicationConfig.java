package bookstore.config;

import bookstore.utils.service.BookService;
import bookstore.utils.service.ReportService;
import bookstore.utils.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ApplicationConfig {

    @Bean
    RmiProxyFactoryBean userService() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://localhost:1099/UserService");
        factoryBean.setServiceInterface(UserService.class);
        return factoryBean;
    }

    @Bean
    RmiProxyFactoryBean bookService() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://localhost:1099/BookService");
        factoryBean.setServiceInterface(BookService.class);
        return factoryBean;
    }

    @Bean
    RmiProxyFactoryBean reportService() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://localhost:1099/ReportService");
        factoryBean.setServiceInterface(ReportService.class);
        return factoryBean;
    }

}
