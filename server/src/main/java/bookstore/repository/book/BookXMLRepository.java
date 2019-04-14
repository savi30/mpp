package bookstore.repository.book;

import bookstore.utils.domain.book.Book;
import bookstore.repository.XMLRepository;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;
import bookstore.utils.exception.ValidationException;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookXMLRepository extends XMLRepository<String, Book> implements BookRepository {
    public BookXMLRepository(Validator<Book> validator, String fileName, Reader<Book> reader) {
        super(validator, fileName, reader);
    }

    /**
     * Buy a book and write changes to file.
     *
     * @param bookId   - id of the bought book
     * @param clientId - id of the client who buys
     * @return an {@code Optional} encapsulating the bought book or empty if no such book is in stock.
     */
    @Override
    public Optional<Book> buy(String bookId, String clientId) {
        Optional<Book> optional = findById(bookId);
        if (optional.isPresent() && entities.get(bookId).getQuantity() > 0) {
            entities.get(bookId).setQuantity(entities.get(bookId).getQuantity() - 1);
            updateXML(optional.get());
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidationException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<Book> optional = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        optional.ifPresentOrElse(opt -> {
            entities.get(entity.getId()).setQuantity(entities.get(entity.getId()).getQuantity() + entity.getQuantity());
            updateXML(entities.get(entity.getId()));
        },
                () -> saveToXML(entity));
        return optional;
    }

    @Override
    public Collection<Book> findByAuthor(String author) {
        return entities.values().stream()
                .filter(book -> book.getAuthorsString().contains(author)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> findByTitle(String title) {
        return entities.values().stream()
                .filter(book -> book.getTitle().contains(title)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> findByDate(Timestamp t1, Timestamp t2) {
        return entities.values().stream()
                .filter(book -> t1.before(book.getPublishYear()) && t2.after(book.getPublishYear()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> findByPrice(Double p1, Double p2) {
        return entities.values().stream()
                .filter(book -> p1 <= book.getPrice() && book.getPrice() <= p2).collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> findByQuantity(Integer quantity) {
        return entities.values().stream()
                .filter(book -> book.getQuantity().equals(quantity)).collect(Collectors.toSet());
    }

}
