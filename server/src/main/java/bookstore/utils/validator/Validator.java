package bookstore.utils.validator;

import bookstore.utils.exception.ValidationException;

/**
 * @author pollos_hermanos.
 */
public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
