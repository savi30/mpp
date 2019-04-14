package bookstore.repository;

import bookstore.utils.domain.core.Entity;
import bookstore.utils.Page;
import bookstore.utils.Pageable;
import bookstore.repository.paging.PagingRepository;
import bookstore.repository.paging.impl.PagingService;
import bookstore.utils.validator.Validator;
import bookstore.utils.exception.ValidationException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public class DBRepository<ID extends Serializable, T extends Entity<ID>> implements PagingRepository<ID, T> {
    protected Validator<T> validator;
    private PagingService pagingService;

    public DBRepository(Validator<T> validator) {
        this.validator = validator;
        this.pagingService = new PagingService();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public Collection<T> findAll() {
        return null;
    }

    @Override
    public Optional<T> update(T entity) throws ValidationException {
        return Optional.empty();
    }

    @Override
    public Optional<T> save(T entity) throws ValidationException {
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        return Optional.empty();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return pagingService.getPagedResponse(this.findAll(), pageable);
    }
}
