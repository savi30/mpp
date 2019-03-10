package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;

import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public interface BookRepository extends Repository<String, Book> {

    Optional<Book> buy(String bookId, String clientId);
}
