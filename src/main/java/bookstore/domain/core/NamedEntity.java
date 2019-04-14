package bookstore.domain.core;

import org.springframework.lang.Nullable;

import javax.persistence.MappedSuperclass;

/**
 * Generic class for an Entity with an ID and a name.
 *
 * @author pollos_hermanos.
 */
@MappedSuperclass
public class NamedEntity<ID> extends Entity<ID> {
    @Nullable
    private String name;

    public NamedEntity(ID id, String name) {
        this.setId(id);
        this.name = name;
    }

    public NamedEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getName();
    }
}
