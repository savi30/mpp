package bookstore.repository.logs;

import bookstore.utils.domain.logs.LogsEntry;
import bookstore.repository.XMLRepository;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;

public class LogsXMLRepository extends XMLRepository<String, LogsEntry> {

    public LogsXMLRepository(Validator<LogsEntry> validator, String fileName,
                             Reader<LogsEntry> reader) {
        super(validator, fileName, reader);
    }
}
