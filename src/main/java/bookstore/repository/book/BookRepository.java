package bookstore.repository.book;

import bookstore.domain.book.Book;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author pollos_hermanos.
 */
public interface BookRepository extends CrudRepository<Book, String> {

    //Optional<Book> buy(String bookId, String clientId);

    Collection<Book> findByAuthors(String author);

    Collection<Book> findByTitle(String title);

    Collection<Book> findByPublishYear(Timestamp t1);

    Collection<Book> findByPublishYearBetween(Timestamp t1, Timestamp t2);

    Collection<Book> findByPrice(Double p1);

    Collection<Book> findByPriceBetween(Double p1, Double p2);

    Collection<Book> findByQuantity(Integer quantity);
}
