package bookstore.repository.paging.impl;

import bookstore.utils.Pageable;

public class PageCursor implements Pageable {
    private int pageSize;
    private int pageNumber;


    public PageCursor(int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
