package bookstore.repository.book;

import bookstore.utils.domain.book.Book;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public interface BookRepository {

    Optional<Book> buy(String bookId, String clientId);

    Collection<Book> findByAuthor(String author);

    Collection<Book> findByTitle(String title);

    Collection<Book> findByDate(Timestamp t1, Timestamp t2);

    Collection<Book> findByPrice(Double p1, Double p2);

    Collection<Book> findByQuantity(Integer quantity);
}
