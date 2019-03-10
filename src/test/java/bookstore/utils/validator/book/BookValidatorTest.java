package bookstore.utils.validator.book;

import bookstore.domain.book.Book;
import bookstore.utils.validator.exception.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookValidatorTest {
    private Book book;
    private BookValidator validator;

    @Before
    public void setUp() throws Exception {
        book = new Book("", "");
        validator = new BookValidator();
    }

    @After
    public void tearDown() throws Exception {
        book = null;
        validator = null;
    }

    @Test
    public void validate() {
        try {
            validator.validate(book);
        } catch (ValidationException e) {
            assertEquals("id must not be null\nTitle must not be empty\nAuthor must not be empty\n", e.getMessage());
        }
        try {
            book.setId("1");
            book.setTitle("Title");
            validator.validate(book);
        } catch (ValidationException e) {
            fail();
        }
    }
}