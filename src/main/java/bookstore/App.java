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
        Repository bookRepository = repositoryFactory.getXMLRepository(Book.class, "Data/BooksXML.xml");
        Repository userRepository = repositoryFactory.getXMLRepository(User.class, "Data/UsersXML.xml");
        Repository logsRepository = repositoryFactory.getXMLRepository(LogsEntry.class, "Data/LogsXML.xml");

        BookService bookService = new BookService(bookRepository);
        UserService userService = new UserService(userRepository);
        LogsService logsService = new LogsService(logsRepository);
        ReportService reportService = new ReportService(bookService, userService, logsService);
        logsService.findAll().forEach(System.out::println);
        System.out.println(reportService.getCustomerWhoSpentMost().toString());
        Console console = new Console(bookService, userService);
        console.runConsole();
    }
}
