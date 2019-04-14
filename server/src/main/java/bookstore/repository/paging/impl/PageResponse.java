package bookstore.repository.paging.impl;

import bookstore.utils.Page;
import bookstore.utils.Pageable;

import java.util.Collection;
import java.util.stream.Stream;

public class PageResponse<T> implements Page<T> {

    private PageCursor pageable;
    private Collection<T> values;

    public PageResponse(PageCursor pageable, Collection<T> values) {
        this.pageable = pageable;
        this.values = values;
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Pageable nextPageable() {
        this.pageable.setPageNumber(this.pageable.getPageNumber() + 1);
        return this.pageable;
    }

    @Override
    public Stream<T> getContent() {
        return values.stream().skip(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize());
    }
}
