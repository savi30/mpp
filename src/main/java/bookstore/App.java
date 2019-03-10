package bookstore;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;
import bookstore.repository.book.BookMySqlRepository;
import bookstore.service.book.BookService;
import bookstore.ui.Console;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.book.BookValidator;

import java.sql.SQLException;

/**
 * @author pollos_hermanos.
 */
public class App {
    public static void main(String[] args) {
        Validator<Book> validator = new BookValidator();
        Repository<Long, Book> repository = null;
        try {
            repository = new BookMySqlRepository(validator);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookService bookService = new BookService(repository);
        Console console = new Console(bookService);
        console.runConsole();
    }
}
