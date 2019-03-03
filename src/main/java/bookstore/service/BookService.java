package bookstore.service;

import bookstore.domain.book.Book;
import bookstore.repository.Repository;

public class BookService extends AbstractCRUDService<Long, Book> {
    public BookService(Repository<Long, Book> repository) {
        this.repository = repository;
    }
}
