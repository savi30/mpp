package bookstore.utils;

import bookstore.repository.Repository;
import bookstore.repository.book.*;
import bookstore.repository.logs.LogsFileRepository;
import bookstore.repository.logs.LogsInMemoryRepository;
import bookstore.repository.logs.LogsMySqlRepository;
import bookstore.repository.logs.LogsXMLRepository;
import bookstore.repository.user.UserFileRepository;
import bookstore.repository.user.UserInMemoryRepository;
import bookstore.repository.user.UserMySqlRepository;
import bookstore.repository.user.UserXMLRepository;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;

public class RepositoryFactory {


    private final static String IN_MEMORY_REPOSITORY = "bookstore.repository.InMemoryRepository";
    private final static String XML_REPOSITORY = "bookstore.repository.LogsXMLRepository";
    private final static String DB_REPOSITORY = "bookstore.repository.DBRepository";

    private static final String BOOK = "bookstore.utils.domain.book.Book";
    private static final String USER = "bookstore.utils.domain.user.User";
    private static final String LOGS = "bookstore.utils.domain.logs.LogsEntry";

    public Repository getRepository(Class<?> objectType) {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator validator = validatorFactory.getValidator(objectType);

        switch (objectType.getName()) {
            case BOOK:
                return new BookInMemoryRepository(validator);
            case USER:
                return new UserInMemoryRepository(validator);
            case LOGS:
                return new LogsInMemoryRepository(validator);
            default:
                return null;
        }

    }

    public Repository getFileRepository(Class<?> objectType, String filename) {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator validator = validatorFactory.getValidator(objectType);
        ReaderFactory readerFactory = new ReaderFactory();
        Reader reader = readerFactory.getBuilder(objectType);

        switch (objectType.getName()) {
            case BOOK:
                return new BookFileRepository(validator, filename, reader);
            case USER:
                return new UserFileRepository(validator, filename, reader);
            case LOGS:
                return new LogsFileRepository(validator, filename, reader);
            default:
                return null;
        }
    }


    public Repository getDBRepository(Class<?> objectType) {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator validator = validatorFactory.getValidator(objectType);
        ReaderFactory readerFactory = new ReaderFactory();
        Reader reader = readerFactory.getBuilder(objectType);

        switch (objectType.getName()) {
            case BOOK:
                return null;
            case USER:
                return new UserMySqlRepository(validator);
            case LOGS:
                return new LogsMySqlRepository(validator);
            default:
                return null;
        }
    }


    public Repository getXMLRepository(Class<?> objectType, String filename) {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator validator = validatorFactory.getValidator(objectType);
        ReaderFactory readerFactory = new ReaderFactory();
        Reader reader = readerFactory.getBuilder(objectType);

        switch (objectType.getName()) {
            case BOOK:
                return new BookXMLRepository(validator, filename, reader);
            case USER:
                return new UserXMLRepository(validator, filename, reader);
            case LOGS:
                return new LogsXMLRepository(validator, filename, reader);
            default:
                return null;
        }
    }

}
