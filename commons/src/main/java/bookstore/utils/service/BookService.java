package bookstore.utils.service;

import bookstore.utils.domain.book.Book;
import bookstore.utils.exception.ValidationException;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

public interface BookService extends CRUDService<String, Book> {

    public Optional<Book> update(Book entity) throws ValidationException;

    public Optional<Book> save(Book entity) throws ValidationException;

    public Optional<Book> buy(String bookId, String clientId);

    public Collection<Book> filterBooksByTitle(String s);

    public Collection<Book> filterBooksByAuthor(String s);

    public Collection<Book> filterBooksByDate(Timestamp t1, Timestamp t2);

    public Collection<Book> filterBooksByPrice(Double p1, Double p2);

    public Collection<Book> filterBooksByQuantity(Integer quantity);
}
