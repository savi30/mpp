package bookstore.service.book;

import bookstore.domain.book.Book;
import bookstore.repository.book.BookRepository;
import bookstore.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;


/**
 * @author pollos_hermanos.
 */
@Component
public class BookService extends CrudService<String, Book> {
    private BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        super(repository);
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
        //return repository.buy(bookId, clientId);
        return Optional.empty();
    }

    /**
     * Return all books whose title contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByTitle(String s) {
        return repository.findByTitle(s);

    }

    /**
     * Return all books whose authors contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByAuthor(String s) {
        return repository.findByAuthors(s);

    }

    /**
     * Return all books whose date is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByDate(Timestamp t1, Timestamp t2) {
        return repository.findByPublishYearBetween(t1, t2);
    }

    /**
     * Return all books whose price is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByPrice(Double p1, Double p2) {
        return repository.findByPriceBetween(p1, p2);
    }

    public Collection<Book> filterBooksByQuantity(Integer quantity) {
        return repository.findByQuantity(quantity);
    }
}