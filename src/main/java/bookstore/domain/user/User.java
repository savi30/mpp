package bookstore.domain.user;

import bookstore.domain.core.NamedEntity;

public class User extends NamedEntity<Long> {
    public User(Long id, String name) {
        super(id, name);
    }
}
