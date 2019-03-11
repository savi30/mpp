package bookstore.domain.book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    private Book book;

    @Before
    public void setUp() throws Exception {
        book = new Book("1", "Title");
    }

    @After
    public void tearDown() throws Exception {
        book = null;
    }

    @Test
    public void getTitleTest() {
        assertEquals("Titles should be the same", book.getTitle(), "Title");
    }

    @Test
    public void setTitleTest() {
        book.setTitle("New Title");
        assertEquals("Titles should be the same", book.getTitle(), "New Title");
    }

    @Test
    public void toStringTest() {
        assertEquals("Strings repr should be the same", book.toString(), "1. Title - Author");
    }
}