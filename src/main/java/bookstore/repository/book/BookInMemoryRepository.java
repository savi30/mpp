package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public class BookInMemoryRepository extends InMemoryRepository<String, Book> {
    public BookInMemoryRepository(Validator<Book> validator) {
        super(validator);
    }

    /**
     * Buy a book.
     * @param bookId - id of the bought book
     * @param clientId - id of the client who buys
     * @return an {@code Optional} encapsulating the bought book or empty if no such book is in stock.
     */
    @Override
    public Optional<Book> buy(String bookId, String clientId){
        Optional<Book> optional = findById(bookId);
        if(optional.isPresent() && entities.get(bookId).getQuantity()>0){
            entities.get(bookId).setQuantity(entities.get(bookId).getQuantity()-1);
            return optional;
        }
        return Optional.empty();
    }
    @Override
    public Optional<Book> save(Book entity) throws ValidationException{
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<Book> optional = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        if( optional.isPresent() && optional.get()!= entity){
            entities.get(entity.getId()).setQuantity(entities.get(entity.getId()).getQuantity() + entity.getQuantity());
        }
        return optional;
    }
}
