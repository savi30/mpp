package bookstore.core.utils.validator.user;

import bookstore.core.domain.user.User;
import bookstore.core.utils.validator.Validator;
import bookstore.core.utils.validator.exception.ValidationException;

/**
 * @author pollos_hermanos.
 */
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        String errors = "";
        if(entity.getId() == null){
            errors += "id must not be null\n";
        }
        if(entity.getName().isEmpty()){
            errors += "Name must not be empty\n";
        }
        if(!entity.getName().matches("[a-zA-Z0-9 ]*"))
            errors += "Name must contain only letters, numbers and spaces!\n";

        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
