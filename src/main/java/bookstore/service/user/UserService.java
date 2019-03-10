package bookstore.service.user;

import bookstore.domain.user.User;
import bookstore.repository.Repository;
import bookstore.service.AbstractCRUDService;

public class UserService extends AbstractCRUDService<Long, User> {
    public UserService(Repository<Long, User> repository) {
        this.repository = repository;
    }
}
