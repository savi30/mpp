package bookstore.domain.user;

import bookstore.domain.core.NamedEntity;

/**
 * @author pollos_hermanos.
 */
public class User extends NamedEntity<String> {
    public User(String id, String name) {
        super(id, name);
    }

    @Override
    public String toString(){
        return super.getId().toString() + ". " + super.getName();
    }

    @Override
    public String toFileString(){
        return super.getId().toString() + "," + super.getName() + "\n";
    }

}
