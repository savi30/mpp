package bookstore.service.user;

import bookstore.domain.user.User;
import bookstore.repository.Repository;
import bookstore.service.AbstractCRUDService;

public class UserService extends AbstractCRUDService<String, User> {
    public UserService(Repository<String, User> repository) {
        this.repository = repository;
    }
}
