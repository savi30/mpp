package bookstore.domain.book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    private Book book;

    @Before
    public void setUp() throws Exception {
        book = new Book(Integer.toUnsignedLong(1), "Title", "Author");
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
    public void getAuthorTest() {
        assertEquals("Authors should be the same", book.getAuthor(), "Author");
    }

    @Test
    public void setTitleTest() {
        book.setTitle("New Title");
        assertEquals("Titles should be the same", book.getTitle(), "New Title");
    }

    @Test
    public void setAuthorTest() {
        book.setAuthor("New Author");
        assertEquals("Authors should be the same", book.getAuthor(), "New Author");
    }

    @Test
    public void toStringTest() {
        assertEquals("Strings repr should be the same", book.toString(), "1. Title - Author");
    }
}