package bookstore.domain.core;

import java.io.Serializable;

/**
 * Class for a generic Entity with an ID.
 * @author pollos_hermanos.
 */
public class Entity<ID> implements Serializable {
    private ID id;

    public Entity(){}

    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }

    public String toFileString(){
        return this.getId() + "\n";
    }

}