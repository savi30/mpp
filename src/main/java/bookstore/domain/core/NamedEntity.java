package bookstore.domain.core;

public class NamedEntity<ID> extends Entity<ID> {
    private String name;

    public NamedEntity(ID id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
