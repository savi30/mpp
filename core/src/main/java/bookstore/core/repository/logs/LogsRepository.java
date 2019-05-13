package bookstore.core.repository.logs;

import bookstore.core.domain.logs.LogsEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends CrudRepository<LogsEntry, Long> {
}
