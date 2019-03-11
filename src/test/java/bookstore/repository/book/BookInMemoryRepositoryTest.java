package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.book.BookValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BookInMemoryRepositoryTest {
    BookInMemoryRepository repository;
    Validator<Book> validator = new BookValidator();
    @Before
    public void setUp() throws Exception {
        repository = new BookInMemoryRepository(validator);
        Book book = new Book("1", "testTitle");
        book.setQuantity(1);
        book.setPrice(12.22);
        book.setPublishYear(Timestamp.valueOf("1999-12-23 00:00:22"));
        List<String> authors = Arrays.asList(("a1 a2").split(" "));
        book.setAuthors(authors.stream().map(a -> new NamedEntity(1, a))
                .collect(Collectors.toList()));
        repository.save(book);
    }

    @After
    public void tearDown() throws Exception {
        repository = null;
        validator = null;
    }

    @Test
    public void buy() {
        assert(repository.buy("1","1").isPresent());
        assert(!repository.buy("1","1").isPresent());
    }
}