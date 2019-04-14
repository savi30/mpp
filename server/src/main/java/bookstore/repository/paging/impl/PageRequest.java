package bookstore.repository.paging.impl;

import bookstore.utils.Pageable;

public class PageRequest implements Pageable {
    private int pageSize;
    private int pageNumber;


    public PageRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }
}
