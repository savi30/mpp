package bookstore.core.domain.core;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Class for a generic Entity with an ID.
 *
 * @author pollos_hermanos.
 */
@MappedSuperclass
public class Entity<ID> {
    @Id
    private ID id;

    public Entity() {
    }

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
}