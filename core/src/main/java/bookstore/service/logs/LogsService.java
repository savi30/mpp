package bookstore.service.logs;

import bookstore.domain.logs.LogsEntry;
import bookstore.repository.logs.LogsRepository;
import bookstore.service.CrudService;
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
