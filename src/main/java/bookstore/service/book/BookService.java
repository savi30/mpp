package bookstore.service.book;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;
import bookstore.service.AbstractCRUDService;

import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public class BookService extends AbstractCRUDService<String, Book> {
    public BookService(Repository<String, Book> repository) {
        this.repository = repository;
    }

    public Optional<Book> buy(String bookId, String clientId) {
        return repository.buy(bookId, clientId);
    }
}