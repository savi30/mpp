package bookstore.config;

import bookstore.repository.book.BookRepositoryImpl;
import bookstore.repository.logs.LogsRepository;
import bookstore.repository.user.UserRepository;
import bookstore.service.ClientServerService;
import bookstore.service.ServerService;
import bookstore.service.book.BookServiceImpl;
import bookstore.service.logs.LogsService;
import bookstore.service.report.ReportServiceImpl;
import bookstore.service.user.UserServiceImpl;
import bookstore.utils.RepositoryFactory;
import bookstore.utils.ValidatorFactory;
import bookstore.utils.domain.book.Book;
import bookstore.utils.domain.logs.LogsEntry;
import bookstore.utils.domain.user.User;
import bookstore.utils.service.BookService;
import bookstore.utils.service.ReportService;
import bookstore.utils.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.concurrent.Executors;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "bookstore")
@PropertySource(value = {"classpath:application.properties"})
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }

    @Bean
    ClientServerService clientServerService() {
        return new ServerService(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    @Bean
    UserService userService() {
        return new UserServiceImpl((UserRepository) new RepositoryFactory().getDBRepository(User.class));
    }

    @Bean
    BookService bookService() {
        return new BookServiceImpl(
                new BookRepositoryImpl(new ValidatorFactory().getValidator(Book.class), jdbcTemplate(dataSource())));
    }

    @Bean
    LogsService logsService() {
        return new LogsService((LogsRepository) new RepositoryFactory().getDBRepository(LogsEntry.class));
    }

    @Bean
    ReportService reportService() {
        return new ReportServiceImpl(bookService(), userService(), logsService());
    }

    @Bean
    public RmiServiceExporter userServiceExporter(UserService userService) {
        Class<UserService> serviceInterface = UserService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(userService);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    @Bean
    public RmiServiceExporter bookServiceExporter(BookService bookService) {
        Class<BookService> serviceInterface = BookService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(bookService);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    @Bean
    public RmiServiceExporter reportServiceExporter(ReportService reportService) {
        Class<ReportService> serviceInterface = ReportService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(reportService);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }
}
