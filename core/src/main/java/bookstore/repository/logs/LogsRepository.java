package bookstore.repository.logs;

import bookstore.domain.logs.LogsEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface LogsRepository extends CrudRepository<LogsEntry, String> {
}
