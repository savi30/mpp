package bookstore.service;

import bookstore.domain.core.Entity;
import bookstore.repository.Repository;
import bookstore.repository.paging.Page;
import bookstore.repository.paging.Pageable;
import bookstore.utils.validator.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public class AbstractCRUDService<ID, T extends Entity<ID>> {
    protected Repository<ID, T> repository;

    public T findById(ID id) {
        Optional<T> entity = repository.findById(id);
        return entity.get();
    }

    public Page<T> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public List<T> findAll() {
        Collection<T> entities = repository.findAll();
        return new ArrayList<>(entities);
    }

    public T update(T entity) throws ValidationException {
        Optional<T> newEntity = repository.update(entity);
        return newEntity.get();
    }

    public void save(T entity) throws ValidationException {
        repository.save(entity);
    }

    public T delete(ID id) {
        Optional<T> entity = repository.delete(id);
        return entity.get();
    }

    public Repository<ID, T> getRepository() {
        return repository;
    }

    public void setRepository(Repository<ID, T> repository) {
        this.repository = repository;
    }
}
