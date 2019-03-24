package bookstore;

import bookstore.domain.book.Book;
import bookstore.domain.logs.LogsEntry;
import bookstore.domain.user.User;
import bookstore.repository.Repository;
import bookstore.service.book.BookService;
import bookstore.service.logs.LogsService;
import bookstore.service.report.ReportService;
import bookstore.service.user.UserService;
import bookstore.ui.Console;
import bookstore.utils.RepositoryFactory;

/**
 * @author pollos_hermanos.
 */
public class App {
    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        Repository bookRepository = repositoryFactory.getDBRepository(Book.class);
        Repository userRepository = repositoryFactory.getDBRepository(User.class);
        Repository logsRepository = repositoryFactory.getXMLRepository(LogsEntry.class, "Data/LogsXML.xml");

        BookService bookService = new BookService(bookRepository);
        UserService userService = new UserService(userRepository);
        LogsService logsService = new LogsService(logsRepository);
        ReportService reportService = new ReportService(bookService, userService, logsService);
        bookService.findAll().forEach(System.out::println);
        Console console = new Console(bookService, userService);
        console.runConsole();
    }
}
