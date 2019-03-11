package bookstore.utils.validator.book;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.utils.validator.exception.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookValidatorTest {
    private Book book;
    private BookValidator validator;
    List<NamedEntity> authors = new ArrayList<NamedEntity>();

    @Before
    public void setUp() throws Exception {
        book = new Book("", "$#12");
        book.setPublishYear(Timestamp.valueOf("2210-12-12 00:00:00"));
        book.setPrice(-21.0);
        book.setAuthors(authors);
        book.setQuantity(-22);
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
            assertEquals("id must not be empty!\nTitle must contain only letters, numbers and spaces!\n" +
                    "Book must at least one author!\nPrice can't be negative!\n" +
                    "Quantity can't be negative!\n", e.getMessage());
        }
        try {
            book.setId("1");
            book.setTitle("Title");
            authors.add(new NamedEntity<String>("1", "Name"));
            book.setQuantity(12);
            book.setPrice(12.1);
            validator.validate(book);
        } catch (ValidationException e) {
            fail();
        }
    }
}