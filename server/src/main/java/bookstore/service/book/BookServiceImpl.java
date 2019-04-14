package bookstore.service.book;

import bookstore.repository.Repository;
import bookstore.repository.book.BookRepository;
import bookstore.service.AbstractCRUDService;
import bookstore.utils.domain.book.Book;
import bookstore.utils.exception.ValidationException;
import bookstore.utils.service.BookService;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;


/**
 * @author pollos_hermanos.
 */
public class BookServiceImpl extends AbstractCRUDService<String, Book> implements BookService {

    public BookServiceImpl(BookRepository repository) {
        this.repository = (Repository<String, Book>) repository;
    }

    @Transactional
    @Override
    public Optional<Book> update(Book entity) throws ValidationException {
        return super.update(entity);
    }

    @Transactional
    @Override
    public Optional<Book> save(Book entity) throws ValidationException {
        return super.save(entity);
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
        return ((BookRepository) repository).buy(bookId, clientId);
    }

    /**
     * Return all books whose title contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByTitle(String s) {
        return ((BookRepository) repository).findByTitle(s);

    }

    /**
     * Return all books whose authors contain the given string.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByAuthor(String s) {
        return ((BookRepository) repository).findByAuthor(s);

    }

    /**
     * Return all books whose date is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByDate(Timestamp t1, Timestamp t2) {
        return ((BookRepository) repository).findByDate(t1, t2);
    }

    /**
     * Return all books whose price is in the given range.
     *
     * @return a set with the filtered books.
     */
    public Collection<Book> filterBooksByPrice(Double p1, Double p2) {
        return ((BookRepository) repository).findByPrice(p1, p2);
    }

    public Collection<Book> filterBooksByQuantity(Integer quantity) {
        return ((BookRepository) repository).findByQuantity(quantity);
    }
}