package bookstore.service;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.repository.book.BookInMemoryRepository;
import bookstore.service.book.BookService;
import bookstore.utils.validator.book.BookValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceTest {
    private BookService bookService;
    private List<NamedEntity> authors = new ArrayList<NamedEntity>();
    private Book book1;
    private Book book2;

    @Before
    public void setUp() throws Exception {
        bookService = new BookService(new BookInMemoryRepository(new BookValidator()));
        book1 = new Book("1", "My First Book");
        book2 = new Book("2", "My Second Book");
        authors.add(new NamedEntity<String>("1", "Name"));
        book1.setPrice(12.1);
        book1.setAuthors(authors);
        book1.setPublishYear(Timestamp.valueOf("2210-12-12 00:00:00"));
        book1.setQuantity(12);
        book2.setPrice(12.1);
        book2.setAuthors(authors);
        book2.setPublishYear(Timestamp.valueOf("2210-12-12 00:00:00"));
        book2.setQuantity(12);
        bookService.save(book1);
        bookService.save(book2);
    }

    @After
    public void tearDown() throws Exception {
        bookService = null;
        book1 = null;
        book2 = null;
    }

    @Test
    public void findById() {
        assertEquals("My First Book", bookService.findById("1").getTitle());
        assertEquals("My Second Book", bookService.findById("2").getTitle());
    }

    @Test
    public void findAll() {
        assertEquals(2, bookService.findAll().size());
        assert(bookService.findAll().contains(book1));
        assert(bookService.findAll().contains(book2));
    }

    @Test
    public void update() throws Exception {
        Book updatedBook = new Book("2", "Changed Title");
        updatedBook.setPrice(12.1);
        updatedBook.setAuthors(authors);
        updatedBook.setPublishYear(Timestamp.valueOf("2210-12-12 00:00:00"));
        updatedBook.setQuantity(12);
        bookService.update(updatedBook);
        assertEquals("Changed Title", bookService.findById("2").getTitle());
        assertEquals("My First Book", bookService.findById("1").getTitle());
    }

    @Test
    public void save() throws Exception {
        Book updatedBook = new Book("1", "Changed Title");
        updatedBook.setPrice(12.1);
        updatedBook.setAuthors(authors);
        updatedBook.setPublishYear(Timestamp.valueOf("2210-12-12 00:00:00"));
        updatedBook.setQuantity(12);
        bookService.save(updatedBook);
        assert(bookService.findAll().size() == 2);
        assert(bookService.findAll().contains(book1));
        assert(bookService.findAll().contains(book2));
        updatedBook.setId("3");
        bookService.save(updatedBook);
        assert(bookService.findAll().size() == 3);
        assert(bookService.findAll().contains(book1));
        assert(bookService.findAll().contains(book2));
        assert(bookService.findAll().contains(updatedBook));
    }

    @Test
    public void delete() {
        assert(bookService.findAll().size() == 2);
        bookService.delete("1");
        assert(bookService.findAll().size() == 1);
        assert(bookService.findAll().contains(book2));
    }

}