package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.repository.FileRepository;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.builder.BookBuilder;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;
import org.checkerframework.checker.nullness.Opt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pollos_hermanos.
 */
public class BookFileRepository extends FileRepository<String, Book> implements BookRepository{

    public BookFileRepository(Validator<Book> validator, String fileName) {
        super(validator, fileName, new BookBuilder());
    }

    /**
     * Buy a book and write changes to file.
     * @param bookId - id of the bought book
     * @param clientId - id of the client who buys
     * @return an {@code Optional} encapsulating the bought book or empty if no such book is in stock.
     */
    public Optional<Book> buy(String bookId, String clientId){
        Optional<Book> optional = findById(bookId);
        if(optional.isPresent() && entities.get(bookId).getQuantity()>0){
            entities.get(bookId).setQuantity(entities.get(bookId).getQuantity()-1);
            writeAllToFile();
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Collection<Book> findByAuthor(String author){
        return entities.values().stream()
                .filter(book -> book.getAuthorsString().contains(author)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> findByTitle(String title){
        return entities.values().stream()
                .filter(book -> book.getTitle().contains(title)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Book> findByDate(Timestamp t1, Timestamp t2) {
        return  entities.values().stream()
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
