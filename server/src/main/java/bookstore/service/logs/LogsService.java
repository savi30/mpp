package bookstore.service.logs;

import bookstore.repository.logs.LogsRepository;
import bookstore.service.AbstractCRUDService;
import bookstore.utils.domain.logs.LogsEntry;

public class LogsService extends AbstractCRUDService<String, LogsEntry> {

    public LogsService(LogsRepository repository) {
        this.repository = repository;
    }
}
