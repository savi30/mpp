package bookstore.service.user;

import bookstore.domain.user.User;
import bookstore.repository.user.UserRepository;
import bookstore.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserService extends CrudService<String, User> {
    private UserRepository repository;
    private static final Logger log = LoggerFactory.getLogger(
            UserService.class);

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Collection<User> filterUsersByName(String s) {
        log.trace("filterUsersByName --- method entered");
        Collection<User> result = repository.findByName(s);
        log.trace("filterUsersByName: result={}", result);
        return result;
    }
}
