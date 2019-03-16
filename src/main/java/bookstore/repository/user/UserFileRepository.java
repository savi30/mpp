package bookstore.repository.user;

import bookstore.domain.user.User;
import bookstore.repository.FileRepository;
import bookstore.utils.reader.UserReader;
import bookstore.utils.validator.Validator;

import java.util.Collection;
import java.util.stream.Collectors;


public class UserFileRepository extends FileRepository<String, User> implements UserRepository {

    public UserFileRepository(Validator<User> validator, String fileName) {
        super(validator, fileName, new UserReader());
    }

    @Override
    public Collection<User> findByName(String name){
        return entities.values().stream()
                .filter(user -> user.getName().contains(name)).collect(Collectors.toSet());
    }
}
