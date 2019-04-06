package bookstore.repository.logs;

import bookstore.domain.logs.LogsEntry;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;

public class LogsInMemoryRepository extends InMemoryRepository<String, LogsEntry> implements LogsRepository {

    public LogsInMemoryRepository(Validator<LogsEntry> validator) {
        super(validator);
    }

}
