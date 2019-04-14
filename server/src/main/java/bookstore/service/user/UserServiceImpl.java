package bookstore.service.user;

import bookstore.repository.user.UserRepository;
import bookstore.service.AbstractCRUDService;
import bookstore.utils.domain.user.User;
import bookstore.utils.service.UserService;

import java.util.Collection;

public class UserServiceImpl extends AbstractCRUDService<String, User> implements UserService {
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public Collection<User> filterUsersByName(String s) {
        return ((UserRepository) repository).findByName(s);
    }
}
