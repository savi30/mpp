package bookstore.utils.domain.user;

import bookstore.utils.domain.core.NamedEntity;

/**
 * @author pollos_hermanos.
 */
public class User extends NamedEntity<String> {
    public User(String id, String name) {
        super(id, name);
    }

    public User(){

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
