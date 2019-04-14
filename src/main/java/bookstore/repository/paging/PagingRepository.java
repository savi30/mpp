package bookstore.repository.paging;

import bookstore.domain.core.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

public interface PagingRepository<ID extends Serializable,
        T extends Entity<ID>>
        extends PagingAndSortingRepository<T, ID> {
    Page<T> findAll(Pageable pageable);
}