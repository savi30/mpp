package bookstore.ui;

import bookstore.domain.book.Book;
import bookstore.service.book.BookService;
import bookstore.utils.validator.exception.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author pollos_hermanos.
 */
public class Console {
    private BookService bookService;

    public Console(BookService bookService) {
        this.bookService = bookService;
    }

    public void runConsole() {
        //addBooks();
        printAllBooks();
    }

    private void printAllBooks() {
        List<Book> books = bookService.findAll();
        books.forEach(System.out::println);
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
        System.out.println("Read book {id, title, author}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String title = bufferRead.readLine();
            String author = bufferRead.readLine();

            Book book = new Book(title, author);
            book.setId(id);

            return book;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
