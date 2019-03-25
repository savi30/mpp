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
        Console console = new Console();
        console.runConsole();
    }
}
