package bookstore.core.repository.logs;

import bookstore.core.domain.logs.LogsEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface LogsRepository extends CrudRepository<LogsEntry, String> {
}
