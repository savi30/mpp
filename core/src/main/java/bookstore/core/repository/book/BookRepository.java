package bookstore.core.repository.book;

import bookstore.core.domain.book.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author pollos_hermanos.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, String> {

    @Modifying
    @Query("update #{#entityName} book set book.quantity = (book.quantity - 1) where book.id = :bookId and book.quantity > 0")
    void buy(@Param("bookId") String bookId);

    Collection<Book> findByAuthors(String author);

    Collection<Book> findByTitle(String title);

    Collection<Book> findByPublishYear(Timestamp t1);

    Collection<Book> findByPublishYearBetween(Timestamp t1, Timestamp t2);

    Collection<Book> findByPrice(Double p1);

    Collection<Book> findByPriceBetween(Double p1, Double p2);

    Collection<Book> findByQuantity(Integer quantity);
}
