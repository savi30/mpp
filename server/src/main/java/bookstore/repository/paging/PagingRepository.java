package bookstore.repository.paging;

import bookstore.utils.domain.core.Entity;
import bookstore.repository.Repository;
import bookstore.utils.Page;
import bookstore.utils.Pageable;

import java.io.Serializable;

public interface PagingRepository<ID extends Serializable,
        T extends Entity<ID>>
        extends Repository<ID, T> {

    Page<T> findAll(Pageable pageable);

}