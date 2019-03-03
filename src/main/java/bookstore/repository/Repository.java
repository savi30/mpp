package bookstore.repository;

import bookstore.domain.core.Entity;
import bookstore.utils.validator.exception.ValidationException;

import java.util.Collection;
import java.util.Optional;

public interface Repository<ID, T extends Entity<ID>> {

    Optional<T> findById(ID id);

    Collection<T> findAll();

    Optional<T> update(T entity) throws ValidationException;

    Optional<T> save(T entity) throws ValidationException;

    Optional<T> delete(ID id);
}
