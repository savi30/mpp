package bookstore.service;

import bookstore.domain.book.Book;
import bookstore.repository.book.BookInMemoryRepository;
import bookstore.service.book.BookService;
import bookstore.utils.validator.book.BookValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookServiceTest {
    private BookService bookService;
    private Book book1;
    private Book book2;

    @Before
    public void setUp() throws Exception {
        bookService = new BookService(new BookInMemoryRepository(new BookValidator()));
        book1 = new Book(Integer.toUnsignedLong(1), "My First Book", "Me");
        book2 = new Book(Integer.toUnsignedLong(2), "My Second Book", "Me");
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
        assertEquals("My First Book", bookService.findById(Integer.toUnsignedLong(1)).getTitle());
        assertEquals("My Second Book", bookService.findById(Integer.toUnsignedLong(2)).getTitle());
    }

    @Test
    public void findAll() {
        assertEquals(2, bookService.findAll().size());
        assert(bookService.findAll().contains(book1));
        assert(bookService.findAll().contains(book2));
    }

    @Test
    public void update() throws Exception {
        Book updatedBook = new Book(Integer.toUnsignedLong(2), "Changed Title", "Still me");
        bookService.update(updatedBook);
        assertEquals("Changed Title", bookService.findById(Integer.toUnsignedLong(2)).getTitle());
        assertEquals("My First Book", bookService.findById(Integer.toUnsignedLong(1)).getTitle());
    }

    @Test
    public void save() throws Exception {
        Book updatedBook = new Book(Integer.toUnsignedLong(1), "Changed Title", "Still me");
        bookService.save(updatedBook);
        assert(bookService.findAll().size() == 2);
        assert(bookService.findAll().contains(book1));
        assert(bookService.findAll().contains(book2));
        updatedBook.setId(Integer.toUnsignedLong(3));
        bookService.save(updatedBook);
        assert(bookService.findAll().size() == 3);
        assert(bookService.findAll().contains(book1));
        assert(bookService.findAll().contains(book2));
        assert(bookService.findAll().contains(updatedBook));
    }

    @Test
    public void delete() {
        assert(bookService.findAll().size() == 2);
        bookService.delete(Integer.toUnsignedLong(1));
        assert(bookService.findAll().size() == 1);
        assert(bookService.findAll().contains(book2));
    }

    @Test
    public void getAllBooks() {
        assert (bookService.findAll().contains(book1));
        assert (bookService.findAll().contains(book2));
    }
}