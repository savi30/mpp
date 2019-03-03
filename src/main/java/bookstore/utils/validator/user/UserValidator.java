package bookstore.utils.validator.user;

import bookstore.domain.user.User;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        // TODO validate the rest of the fields
    }
}
