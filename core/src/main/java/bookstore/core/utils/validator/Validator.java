package bookstore.core.utils.validator;

import bookstore.core.utils.validator.exception.ValidationException;

/**
 * @author pollos_hermanos.
 */
public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
