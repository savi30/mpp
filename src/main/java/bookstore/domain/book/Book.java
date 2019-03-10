package bookstore.domain.book;

import bookstore.domain.core.NamedEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @author pollos_hermanos.
 */
public class Book extends NamedEntity<String> {
    private String title;
    private List<NamedEntity> authors = new ArrayList<>();
    private Timestamp publishYear;
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
        return this.getId() + ". " + title + " - " + authors.toString() + "-" + publishYear
                .toLocalDateTime() + "-" + quantity + "-" + price + "$";
    }

    public List<NamedEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(List<NamedEntity> authors) {
        this.authors = authors;
    }
}
