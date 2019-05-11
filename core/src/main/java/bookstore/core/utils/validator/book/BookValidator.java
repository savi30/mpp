package bookstore.core.utils.validator.book;

import bookstore.core.domain.book.Book;
import bookstore.core.utils.validator.Validator;
import bookstore.core.utils.validator.exception.ValidationException;

/**
 * @author pollos_hermanos.
 */
public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidationException {
        String errors = "";
        if(entity.getId().isEmpty()){
            errors += "id must not be empty!\n";
        }
        if(!entity.getTitle().matches("[a-zA-Z0-9 ]+")){
            errors += "Title must contain only letters, numbers and spaces!\n";
        }
        if(entity.getAuthorsString().isEmpty()){
            errors += "Book must at least one author!\n";
        }
        else if(!entity.getAuthorsString().matches("[a-zA-Z0-9.; ]*"))
            errors += "Author names must contain only letters, numbers, and spaces!\n";
        if(entity.getPrice() < 0)
            errors += "Price can't be negative!\n";
        if(entity.getQuantity() < 0){
            errors += "Quantity can't be negative!\n";
        }
        if(entity.getPublishYear() == null)
            errors += "Publish year can't be null!\n";
        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
