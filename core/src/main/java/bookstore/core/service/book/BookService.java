package bookstore.core.service.book;

import bookstore.core.domain.book.Book;
import bookstore.core.domain.logs.LogsEntry;
import bookstore.core.repository.book.BookRepository;
import bookstore.core.repository.logs.LogsRepository;
import bookstore.core.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;


/**
 * @author pollos_hermanos.
 */
@Component
public class BookService extends CrudService<String, Book> {
    private BookRepository repository;
    private final LogsRepository logsRepository;
    private static final Logger log = LoggerFactory.getLogger(
            BookService.class);

    @Autowired
    public BookService(BookRepository repository, LogsRepository logsRepository) {
        super(repository);
        this.repository = repository;
        this.logsRepository = logsRepository;
    }

    /**
     * Buy a book.
     *
     * @param bookId   - id of the bought book
     * @param clientId - id of the client who buys
     * @return an {@code Optional} encapsulating the bought book or empty if no such book is in stock.
     */
    @Transactional
    public Optional<Book> buy(String bookId, String clientId) {
        repository.buy(bookId);
        logsRepository.save(new LogsEntry(clientId, bookId, null));
        return repository.findById(bookId);
    }

    /**
     * Return all books whose title contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByTitle(String s) {
        log.trace("filterBooksByTitle --- method entered");
        Collection<Book> result = repository.findByTitle(s);
        log.trace("filterBooksByTitle: result={}", result);
        return result;
    }

    /**
     * Return all books whose authors contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByAuthor(String s) {
        log.trace("filterBooksByAuthor --- method entered");
        Collection<Book> result = repository.findByAuthors(s);
        log.trace("filterBooksByAuthor: result={}", result);
        return result;

    }

    /**
     * Return all books whose date is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByDate(Timestamp t1, Timestamp t2) {
        log.trace("filterBooksByDate --- method entered");
        Collection<Book> result = repository.findByPublishYearBetween(t1, t2);
        log.trace("filterBooksByDate: result={}", result);
        return result;
    }

    /**
     * Return all books whose price is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByPrice(Double p1, Double p2) {
        log.trace("filterBooksByPrice --- method entered");
        Collection<Book> result = repository.findByPriceBetween(p1, p2);
        log.trace("filterBooksByPrice: result={}", result);
        return result;
    }

    public Collection<Book> filterBooksByQuantity(Integer quantity) {
        log.trace("filterBooksByQuantity --- method entered");
        Collection<Book> result = repository.findByQuantity(quantity);
        log.trace("filterBooksByQuantity: result={}", result);
        return result;
    }
}