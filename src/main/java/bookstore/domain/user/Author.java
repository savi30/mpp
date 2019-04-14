package bookstore.domain.user;

import bookstore.domain.core.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
public class Author extends NamedEntity<String> {
    public Author(String id, String name) {
        super(id, name);
    }

    Author() {
    }
}
