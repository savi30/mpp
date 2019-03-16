package bookstore.utils;

import bookstore.utils.validator.LogsValidator;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.book.BookValidator;
import bookstore.utils.validator.user.UserValidator;

public class ValidatorFactory {
    private static final String BOOK_VALIDATOR = "bookstore.domain.book.Book";
    private static final String USER_VALIDATOR = "bookstore.domain.user.User";
    private static final String LOGS_VALIDATOR = "bookstore.domain.logs.LogsEntry";

    public Validator getValidator(Class<?> type) {
        switch (type.getName()) {
            case BOOK_VALIDATOR:
                return new BookValidator();
            case USER_VALIDATOR:
                return new UserValidator();
            case LOGS_VALIDATOR:
                return new LogsValidator();
            default:
                return null;
        }
    }
}
