package bookstore.utils.builder;

import bookstore.domain.core.Entity;

public class EntityBuilder implements Builder<Entity<String>> {
    public Entity<String> get(String line){
        return new Entity<>();
    }
}
