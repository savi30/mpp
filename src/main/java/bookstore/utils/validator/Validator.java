package bookstore.utils.validator;

import bookstore.utils.validator.exception.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
