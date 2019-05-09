package bookstore.repository.paging.impl;

import bookstore.repository.paging.Page;
import bookstore.repository.paging.Pageable;

import java.util.Collection;

public class PagingService<T> {
    public Page<T> getPagedResponse(Collection<T> all,
                                    Pageable pageRequest) {
        PageCursor pageCursor = new PageCursor(pageRequest.getPageSize(), pageRequest.getPageNumber());
        return new PageResponse<>(pageCursor, all);
    }
}
