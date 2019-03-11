package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;

import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public class BookInMemoryRepository extends InMemoryRepository<String, Book> {
    public BookInMemoryRepository(Validator<Book> validator) {
        super(validator);
    }
    @Override
    public Optional<Book> buy(String bookId, String clientId){
        Optional<Book> optional = findById(bookId);
        if(optional.isPresent() && entities.get(bookId).getQuantity()>0){
            entities.get(bookId).setQuantity(entities.get(bookId).getQuantity()-1);
            return optional;
        }
        return Optional.empty();
    }
}
