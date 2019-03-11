package bookstore.service.user;

import bookstore.domain.book.Book;
import bookstore.domain.user.User;
import bookstore.repository.Repository;
import bookstore.service.AbstractCRUDService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserService extends AbstractCRUDService<String, User> {
    public UserService(Repository<String, User> repository) {
        this.repository = repository;
    }

    public Set<User> filterUsersByName(String s){
        Iterable<User> users = repository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .filter(user -> user.getName().contains(s)).collect(Collectors.toSet());

    }
}
