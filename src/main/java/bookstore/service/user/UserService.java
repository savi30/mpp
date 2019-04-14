package bookstore.service.user;

import bookstore.domain.user.User;
import bookstore.repository.user.UserRepository;
import bookstore.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserService extends CrudService<String, User> {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Collection<User> filterUsersByName(String s) {
        return repository.findByName(s);
    }
}
