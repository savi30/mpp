package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;

/**
 * @author pollos_hermanos.
 */
public class BookInMemoryRepository extends InMemoryRepository<Long, Book> {
    public BookInMemoryRepository(Validator<Book> validator) {
        super(validator);
    }
}
