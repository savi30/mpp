package bookstore.repository.user;

import bookstore.repository.Repository;
import bookstore.utils.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface UserRepository extends Repository<String, User> {
    Collection<User> findByName(String name);
}
