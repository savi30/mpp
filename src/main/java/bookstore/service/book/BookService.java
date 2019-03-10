package bookstore.service.book;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;
import bookstore.service.AbstractCRUDService;

/**
 * @author pollos_hermanos.
 */
public class BookService extends AbstractCRUDService<Long, Book> {
    public BookService(Repository<Long, Book> repository) {
        this.repository = repository;
    }
}