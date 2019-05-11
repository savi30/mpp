package bookstore.core.repository.user;

import bookstore.core.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, String> {
    Collection<User> findByName(String name);
}
