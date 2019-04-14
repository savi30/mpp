package bookstore.service;

import bookstore.repository.Repository;
import bookstore.utils.Page;
import bookstore.utils.Pageable;
import bookstore.utils.domain.core.Entity;
import bookstore.utils.exception.ValidationException;
import bookstore.utils.service.CRUDService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public class AbstractCRUDService<ID, T extends Entity<ID>> implements CRUDService<ID, T> {
    protected Repository<ID, T> repository;

    public T findById(ID id) {
        Optional<T> entity = repository.findById(id);
        return entity.get();
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<T> findAll() {
        Collection<T> entities = repository.findAll();
        return new ArrayList<>(entities);
    }

    public Optional<T> update(T entity) throws ValidationException {
        return repository.update(entity);
    }

    public Optional<T> save(T entity) throws ValidationException {
        return repository.save(entity);
    }

    public Optional<T> delete(ID id) {
        return repository.delete(id);
    }

    public Repository<ID, T> getRepository() {
        return repository;
    }

    public void setRepository(Repository<ID, T> repository) {
        this.repository = repository;
    }
}
