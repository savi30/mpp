package bookstore.utils;

import bookstore.repository.*;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;

public class RepositoryFactory {
    private final static String IN_MEMORY_REPOSITORY = "bookstore.repository.InMemoryRepository";
    private final static String XML_REPOSITORY = "bookstore.repository.LogsXMLRepository";
    private final static String DB_REPOSITORY = "bookstore.repository.DBRepository";

    public Repository getRepository(Class<?> repoType, Class<?> objectType) {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator validator = validatorFactory.getValidator(objectType);

        switch (repoType.getName()) {
            case IN_MEMORY_REPOSITORY:
                return new InMemoryRepository(validator);
/*            case XML_REPOSITORY:
                //TODO return XML repo after it's created
                return null;*/
            case DB_REPOSITORY:
                return new DBRepository(validator);
            default:
                return null;
        }
    }

    public Repository getFileRepository(Class<?> objectType, String filename) {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator validator = validatorFactory.getValidator(objectType);
        ReaderFactory readerFactory = new ReaderFactory();
        Reader reader = readerFactory.getBuilder(objectType);

        return new FileRepository(validator, filename, reader);
    }

    public Repository getXMLRepository(Class<?> objectType, String filename) {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator validator = validatorFactory.getValidator(objectType);
        ReaderFactory readerFactory = new ReaderFactory();
        Reader reader = readerFactory.getBuilder(objectType);

        return new XMLRepository(validator, filename, reader);
    }

}
