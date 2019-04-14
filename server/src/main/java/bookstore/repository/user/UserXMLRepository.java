package bookstore.repository.user;

import bookstore.utils.domain.user.User;
import bookstore.repository.XMLRepository;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserXMLRepository extends XMLRepository<String, User> implements UserRepository {
    public UserXMLRepository(Validator<User> validator, String fileName, Reader<User> reader) {
        super(validator, fileName, reader);
    }

    @Override
    public Collection<User> findByName(String name) {
        return entities.values().stream()
                .filter(user -> user.getName().contains(name)).collect(Collectors.toSet());
    }
}
