package bookstore.domain.book;

import bookstore.domain.core.NamedEntity;

public class Book extends NamedEntity<Long> {
    private String title;
    private String author;

    public Book(Long id, String name) {
        super(id, name);
    }
}
