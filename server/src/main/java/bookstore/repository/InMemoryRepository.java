package bookstore.repository;

import bookstore.domain.core.Entity;
import bookstore.repository.paging.Page;
import bookstore.repository.paging.Pageable;
import bookstore.repository.paging.PagingRepository;
import bookstore.repository.paging.impl.PagingService;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

import java.io.Serializable;
import java.util.*;

/**
 * @author pollos_hermanos.
 */
public class InMemoryRepository<ID extends Serializable, T extends Entity<ID>> implements Repository<ID, T>, PagingRepository<ID, T> {

    protected Map<ID, T> entities;
    protected Validator<T> validator;
    private PagingService<T> pagingService;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        this.entities = new HashMap<>();
        this.pagingService = new PagingService<>();
    }

    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Collection<T> findAll() {
        return new HashSet<>(entities.values());
    }

    @Override
    public Optional<T> save(T entity) throws ValidationException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidationException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return pagingService.getPagedResponse(this.findAll(), pageable);
    }
}
