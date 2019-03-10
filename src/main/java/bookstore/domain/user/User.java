package bookstore.domain.user;

import bookstore.domain.core.NamedEntity;

/**
 * @author pollos_hermanos.
 */
public class User extends NamedEntity<Long> {
    public User(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString(){
        return super.getId().toString() + ". " + super.getName();
    }
}
