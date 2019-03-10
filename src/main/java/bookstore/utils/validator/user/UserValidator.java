package bookstore.utils.validator.user;

import bookstore.domain.user.User;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

/**
 * @author pollos_hermanos.
 */
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        // TODO validate the rest of the fields
        String errors = "";
        if(entity.getId() == null){
            errors += "id must not be null\n";
        }
        if(entity.getName().isEmpty()){
            errors += "Name must not be empty\n";
        }

        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
