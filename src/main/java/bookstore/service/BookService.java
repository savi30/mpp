package bookstore.service;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author pollos_hermanos.
 */
public class BookService extends AbstractCRUDService<Long, Book> {
    public BookService(Repository<Long, Book> repository) {
        this.repository = repository;
    }

    /**
     * Returns all books.
     *
     * @return all books.
     */
    public Set<Book> getAllBooks(){
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
    }
}
