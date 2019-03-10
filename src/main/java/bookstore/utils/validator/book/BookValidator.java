package bookstore.utils.validator.book;

import bookstore.domain.book.Book;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

/**
 * @author pollos_hermanos.
 */
public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidationException {
        // TODO validate the rest of the fields
        String errors = "";
        if(entity.getId() == null){
            errors += "id must not be null\n";
        }
        if(entity.getTitle().isEmpty()){
            errors += "Title must not be empty\n";
        }

        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
