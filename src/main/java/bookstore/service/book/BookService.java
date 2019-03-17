package bookstore.service.book;

import bookstore.domain.book.Book;
import bookstore.repository.book.BookRepository;
import bookstore.service.AbstractCRUDService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;


/**
 * @author pollos_hermanos.
 */
public class BookService extends AbstractCRUDService<String, Book> {
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    /**
     * Buy a book.
     *
     * @param bookId   - id of the bought book
     * @param clientId - id of the client who buys
     * @return an {@code Optional} encapsulating the bought book or empty if no such book is in stock.
     */
    public Optional<Book> buy(String bookId, String clientId) {
        return ((BookRepository)repository).buy(bookId, clientId);
    }

    /**
     * Return all books whose title contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByTitle(String s) {
        return ((BookRepository)repository).findByTitle(s);

    }

    /**
     * Return all books whose authors contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByAuthor(String s) {
        return ((BookRepository)repository).findByAuthor(s);

    }

    /**
     * Return all books whose date is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByDate(Timestamp t1, Timestamp t2) {
        return ((BookRepository)repository).findByDate(t1, t2);
    }

    /**
     * Return all books whose price is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByPrice(Double p1, Double p2) {
        return ((BookRepository)repository).findByPrice(p1, p2);
    }

    public Collection<Book> filterBooksByQuantity(Integer quantity) {
        return ((BookRepository)repository).findByQuantity(quantity);
    }
}