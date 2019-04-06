package bookstore.service.logs;

import bookstore.domain.logs.LogsEntry;
import bookstore.repository.Repository;
import bookstore.service.AbstractCRUDService;

public class LogsService extends AbstractCRUDService<String, LogsEntry> {

    public LogsService(Repository<String, LogsEntry> repository) {
        this.repository = repository;
    }
}
