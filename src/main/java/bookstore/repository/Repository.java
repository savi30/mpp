package bookstore.repository;

import bookstore.domain.core.Entity;
import bookstore.utils.validator.exception.ValidationException;

import java.util.Collection;
import java.util.Optional;


/**
 * Interface for generic CRUD operations on a repository for a specific type.
 *
 * @author pollos_hermanos.
 */
public interface Repository<ID, T extends Entity<ID>> {

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional<T> findById(ID id);

    /**
     * Return all entities.
     *
     * @return all entities.
     */
    Collection<T> findAll();

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidationException      if the entity is not valid.
     */
    Optional<T> update(T entity) throws ValidationException;

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidationException      if the entity is not valid.
     */
    Optional<T> save(T entity) throws ValidationException;

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional<T> delete(ID id);


    Optional<T> buy(ID bookId, ID clientId);
}
