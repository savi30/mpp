package bookstore.ui;

import bookstore.domain.book.Book;
import bookstore.domain.user.User;
import bookstore.service.book.BookService;
import bookstore.service.user.UserService;
import bookstore.utils.validator.exception.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

/**
 * @author pollos_hermanos.
 */
public class Console {
    private BookService bookService;
    private UserService userService;

    public Console(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public void runConsole() {
        //addBooks();
        printAllBooks();
        printAllUsers();
    }

    private void printAllBooks() {
        List<Book> books = bookService.findAll();
        books.forEach(System.out::println);
    }

    private void printAllUsers(){
        List<User> users = userService.findAll();
        users.forEach(System.out::println);
    }

    private void addBooks() {
        while (true) {
            Book book = readBook();
            if (book == null || book.getId() < 0) {
                break;
            }
            try {
                bookService.save(book);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
    }

    private Book readBook() {
        System.out.println("Read book {id, title, author, yyyy-mm-dd}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String title = bufferRead.readLine();
            String author = bufferRead.readLine();
            String dateString = bufferRead.readLine();
            LocalDate date = LocalDate.parse(dateString);

            Book book = new Book(title, author);
            book.setId(id);
            book.setPublishYear(date);

            return book;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
