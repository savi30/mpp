package bookstore.domain.user;

import bookstore.domain.core.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author pollos_hermanos.
 */
@Entity
@Table(name = "users")
public class User extends NamedEntity<String> {
    public User(String id, String name) {
        super(id, name);
    }

    public User() {
    }

    @Override
    public String toString() {
        return super.getId() + ". " + super.getName();
    }

}
