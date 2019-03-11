package bookstore;

import bookstore.domain.book.Book;
import bookstore.domain.user.User;
import bookstore.repository.Repository;
import bookstore.repository.book.BookMySqlRepository;
import bookstore.repository.user.UserFileRepository;
import bookstore.service.book.BookService;
import bookstore.service.user.UserService;
import bookstore.ui.Console;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.book.BookValidator;
import bookstore.utils.validator.user.UserValidator;

/**
 * @author pollos_hermanos.
 */
public class App {
    public static void main(String[] args) {
        Validator<Book> bookValidator = new BookValidator();
        Validator<User> userValidator = new UserValidator();
        Repository<String, Book> bookRepository = new BookMySqlRepository(bookValidator);
        Repository<String, User> userRepository = new UserFileRepository(userValidator, "./data/Users");

        BookService bookService = new BookService(bookRepository);
        UserService userService = new UserService(userRepository);
        bookService.findAll().forEach(System.out::println);
        Console console = new Console(bookService, userService);
        console.runConsole();
    }
}
