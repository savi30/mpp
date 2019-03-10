package bookstore.repository.user;

import bookstore.domain.user.User;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;

public class UserInMemoryRepository extends InMemoryRepository<String, User> {
    public UserInMemoryRepository(Validator<User> validator){
        super(validator);
    }
}
