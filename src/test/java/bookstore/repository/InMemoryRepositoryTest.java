package bookstore.repository;

import bookstore.domain.book.Book;
import bookstore.utils.validator.book.BookValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository<Long, Book> repository;
    private Book book;


    @Before
    public void setUp() throws Exception {
        repository = new InMemoryRepository<>(new BookValidator());
        book = new Book(Integer.toUnsignedLong(1), "My Book", "Me");
        repository.save(book);
    }

    @After
    public void tearDown() throws Exception {
        repository = null;
        book = null;
    }

    @Test
    public void findById() {
        assertEquals("Books should be the same", repository.findById(Integer.toUnsignedLong(1)).get(), book);
    }

    @Test
    public void findAll() {
        assert (repository.findAll().contains(book));
        assert (repository.findAll().size() == 1);
    }

    @Test
    public void save() throws Exception {
        Book newBook = new Book(Integer.toUnsignedLong(1), "Changed Book", "Still me");
        repository.save(newBook);
        assertEquals("The book should be the same as before", "My Book", repository.findById(Integer.toUnsignedLong(1)).get().getTitle());
        newBook.setId(Integer.toUnsignedLong(2));
        repository.save(newBook);
        assert (repository.findAll().size() == 2);
        assertEquals("The names should be the same", "Changed Book", repository.findById(Integer.toUnsignedLong(2)).get().getTitle());
    }

    @Test
    public void delete() throws Exception {
        Book newBook = new Book(Integer.toUnsignedLong(2), "Changed Book", "Still me");
        repository.save(newBook);
        repository.delete(Integer.toUnsignedLong(1));
        assert (repository.findAll().size() == 1);
        assertEquals("Changed Book", repository.findById(Integer.toUnsignedLong(2)).get().getTitle());
    }

    @Test
    public void update() throws Exception {
        Book newBook = new Book(Integer.toUnsignedLong(1), "Changed Book", "Still me");
        repository.update(newBook);
        assertEquals("Name should be changed now", "Changed Book", repository.findById(Integer.toUnsignedLong(1)).get().getTitle());
    }
}