package bookstore.utils.service;

import bookstore.utils.domain.user.User;

import java.util.Collection;

public interface UserService extends CRUDService<String, User> {

    public Collection<User> filterUsersByName(String s);

}
