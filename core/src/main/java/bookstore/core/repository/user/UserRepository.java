package bookstore.core.repository.user;

import bookstore.core.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Collection<User> findByName(String name);
}
