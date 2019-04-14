package bookstore.utils.service;

import bookstore.utils.Page;
import bookstore.utils.Pageable;
import bookstore.utils.domain.core.Entity;
import bookstore.utils.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public interface CRUDService<ID, T extends Entity<ID>> {

    public T findById(ID id);

    public Page<T> findAll(Pageable pageable);

    public List<T> findAll();

    public Optional<T> update(T entity) throws ValidationException;

    public Optional<T> save(T entity) throws ValidationException;

    public Optional<T> delete(ID id);

}
