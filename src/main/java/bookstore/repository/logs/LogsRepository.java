package bookstore.repository.logs;

import bookstore.domain.logs.LogsEntry;
import org.springframework.data.repository.CrudRepository;

public interface LogsRepository extends CrudRepository<LogsEntry, String> {
}
