package bookstore.utils.validator.book;

import bookstore.domain.book.Book;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;


public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidationException {
        // TODO validate the rest of the fields
    }
}
