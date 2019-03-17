package bookstore.repository.user;

import bookstore.domain.user.User;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserInMemoryRepository extends InMemoryRepository<String, User> implements UserRepository {
    public UserInMemoryRepository(Validator<User> validator){
        super(validator);
    }

    @Override
    public Collection<User> findByName(String name){
        return entities.values().stream()
                .filter(user -> user.getName().contains(name)).collect(Collectors.toSet());
    }
}
