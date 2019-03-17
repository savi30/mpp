package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;
import bookstore.utils.validator.exception.ValidationException;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public interface BookRepository extends Repository<String, Book> {

    Optional<Book> buy(String bookId, String clientId);
    @Override
    Optional<Book> save(Book entity)throws ValidationException;
    Collection<Book> findByAuthor(String author);
    Collection<Book> findByTitle(String title);
    Collection<Book> findByDate(Timestamp t1, Timestamp t2);
    Collection<Book> findByPrice(Double p1, Double p2);
    Collection<Book> findByQuantity(Integer quantity);
}
