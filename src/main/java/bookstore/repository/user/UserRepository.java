package bookstore.repository.user;

import bookstore.domain.user.User;
import bookstore.repository.Repository;

import java.util.Collection;

public interface UserRepository extends Repository<String, User> {
    Collection<User> findByName(String name);
}
