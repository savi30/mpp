package bookstore.repository.user;

import bookstore.domain.user.User;
import bookstore.repository.XMLRepository;
import bookstore.utils.reader.UserReader;
import bookstore.utils.validator.Validator;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserXMLRepository extends XMLRepository<String, User> implements UserRepository {
    public UserXMLRepository(Validator<User> validator, String fileName) {
        super(validator, fileName, new UserReader());
    }

    @Override
    public Collection<User> findByName(String name){
        return entities.values().stream()
                .filter(user -> user.getName().contains(name)).collect(Collectors.toSet());
    }
}
