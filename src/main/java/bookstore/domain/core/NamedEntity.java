package bookstore.domain.core;

/**
 * Generic class for an Entity with an ID and a name.
 * @author pollos_hermanos.
 */
public class NamedEntity<ID> extends Entity<ID> {
    private String name;

    public NamedEntity(ID id, String name) {
        this.setId(id);
        this.name = name;
    }

    public NamedEntity(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
