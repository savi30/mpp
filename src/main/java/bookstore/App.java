package bookstore;

import bookstore.domain.book.Book;
import bookstore.domain.user.User;
import bookstore.repository.Repository;
import bookstore.repository.book.BookFileRepository;
import bookstore.repository.book.BookMySqlRepository;
import bookstore.repository.book.BookRepository;
import bookstore.repository.user.UserFileRepository;
import bookstore.repository.user.UserRepository;
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
        BookRepository bookRepository = new BookFileRepository(bookValidator, "./data/Books");
        UserRepository userRepository = new UserFileRepository(userValidator, "./data/Users");

        BookService bookService = new BookService(bookRepository);
        UserService userService = new UserService(userRepository);
        bookService.filterBooksByAuthor("author").forEach(System.out::println);
        userService.filterUsersByName("User").forEach(System.out::println);
        Console console = new Console(bookService, userService);
        console.runConsole();
    }
}
