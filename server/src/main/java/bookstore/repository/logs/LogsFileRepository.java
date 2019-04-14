package bookstore.repository.logs;

import bookstore.utils.domain.logs.LogsEntry;
import bookstore.repository.FileRepository;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;

public class LogsFileRepository extends FileRepository<String, LogsEntry> {

    public LogsFileRepository(Validator<LogsEntry> validator, String fileName,
                              Reader<LogsEntry> reader) {
        super(validator, fileName, reader);
    }
}
