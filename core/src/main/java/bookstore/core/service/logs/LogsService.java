package bookstore.core.service.logs;

import bookstore.core.domain.logs.LogsEntry;
import bookstore.core.repository.logs.LogsRepository;
import bookstore.core.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogsService extends CrudService<String, LogsEntry> {

    private LogsRepository repository;

    @Autowired
    public LogsService(LogsRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
