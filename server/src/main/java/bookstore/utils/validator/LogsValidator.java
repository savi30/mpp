package bookstore.utils.validator;

import bookstore.utils.domain.logs.LogsEntry;
import bookstore.utils.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class LogsValidator implements Validator<LogsEntry> {
    @Override
    public void validate(LogsEntry entity) throws ValidationException {

    }
}
