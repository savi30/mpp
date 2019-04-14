package bookstore.utils.validator.user;

import bookstore.utils.domain.user.User;
import bookstore.utils.validator.Validator;
import bookstore.utils.exception.ValidationException;
import org.springframework.stereotype.Component;

/**
 * @author pollos_hermanos.
 */
@Component
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        String errors = "";
        if (entity.getId() == null) {
            errors += "id must not be null\n";
        }
        if (entity.getName().isEmpty()) {
            errors += "Name must not be empty\n";
        }
        if (!entity.getName().matches("[a-zA-Z0-9 ]*"))
            errors += "Name must contain only letters, numbers and spaces!\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
