package bookstore.domain.book;

import bookstore.domain.core.NamedEntity;

import java.util.Date;


/**
 * @author pollos_hermanos.
 */
public class Book extends NamedEntity<Long> {
    private String title;
    private String author;
    private Date publishYear;

    public Book() {
    }

    public Book(Long id, String name) {
        super(id, name);
        title = name;
    }

    public Book(Long id, String title, String author) {
        super(id, title);
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Date publishYear) {
        this.publishYear = publishYear;
    }

    @Override
    public String toString() {
        return this.getId() + ". " + title + " - " + author;
    }

}
