package bookstore.core.domain.book;

import bookstore.core.domain.core.NamedEntity;
import bookstore.core.domain.user.Author;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author pollos_hermanos.
 */
@Entity
@Table(name = "books")
@AttributeOverride(name = "name", column = @Column(name = "title", insertable = false, updatable = false))
public class Book extends NamedEntity<String> {
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    @Column(name = "publish_year")
    private Timestamp publishYear = null;

    private Double price;

    private Integer quantity;

    public Book() {
    }

    public Book(String id, String title) {
        super(id, title);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public Timestamp getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Timestamp publishYear) {
        this.publishYear = publishYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.getId() + ". " + title + " - " + getAuthorsString() + " - " + publishYear
                .toLocalDateTime() + " - " + quantity + " - " + price + "$";
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getAuthorsString() {
        return authors.stream().map(author -> author.getId() + "." + author.getName())
                .collect(Collectors.joining(";"));
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
