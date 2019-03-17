package bookstore.service.user;

import bookstore.domain.user.User;
import bookstore.repository.user.UserRepository;
import bookstore.service.AbstractCRUDService;

import java.util.Collection;

public class UserService extends AbstractCRUDService<String, User> {
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Collection<User> filterUsersByName(String s){
        return ((UserRepository)repository).findByName(s);
    }
}
