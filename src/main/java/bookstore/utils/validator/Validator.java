package bookstore.utils.validator;

import bookstore.utils.validator.exception.ValidationException;

/**
 * @author pollos_hermanos.
 */
public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
