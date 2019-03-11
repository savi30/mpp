package bookstore.service.book;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;
import bookstore.service.AbstractCRUDService;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author pollos_hermanos.
 */
public class BookService extends AbstractCRUDService<String, Book> {
    public BookService(Repository<String, Book> repository) {
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
        return repository.buy(bookId, clientId);
    }

    /**
     * Return all books whose title contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Set<Book> filterBooksByTitle(String s) {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .filter(book -> book.getTitle().contains(s)).collect(Collectors.toSet());

    }

    /**
     * Return all books whose authors contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Set<Book> filterBooksByAuthor(String s) {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .filter(book -> book.getAuthorsString().contains(s)).collect(Collectors.toSet());

    }

    /**
     * Return all books whose date is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Set<Book> filterBooksByDate(Timestamp t1, Timestamp t2) {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .filter(book -> t1.before(book.getPublishYear()) && t2.after(book.getPublishYear()))
                .collect(Collectors.toSet());
    }

    /**
     * Return all books whose price is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Set<Book> filterBooksByPrice(Double p1, Double p2) {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .filter(book -> p1 <= book.getPrice() && book.getPrice() <= p2).collect(Collectors.toSet());
    }

    public Set<Book> filterBooksByQuantity(Integer quantity) {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .filter(book -> book.getQuantity() == quantity).collect(Collectors.toSet());
    }
}