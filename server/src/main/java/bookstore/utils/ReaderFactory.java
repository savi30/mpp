package bookstore.utils;

import bookstore.utils.reader.BookReader;
import bookstore.utils.reader.LogReader;
import bookstore.utils.reader.Reader;
import bookstore.utils.reader.UserReader;

public class ReaderFactory {
    private static final String BOOK_READER = "bookstore.utils.domain.book.Book";
    private static final String USER_READER = "bookstore.utils.domain.user.User";
    private static final String LOGS_READER = "bookstore.utils.domain.logs.LogsEntry";

    public Reader getBuilder(Class<?> type) {
        switch (type.getName()) {
            case BOOK_READER:
                return new BookReader();
            case USER_READER:
                return new UserReader();
            case LOGS_READER:
                return new LogReader();
            default:
                return null;
        }
    }
}
