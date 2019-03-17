package bookstore.utils.builder;

import bookstore.domain.user.User;

import java.util.Arrays;
import java.util.List;

public class UserBuilder implements Builder<User> {
    public User get(String line){
        List<String> items = Arrays.asList(line.split(","));

        String id = String.valueOf(items.get(0));
        String name = items.get(1);

        return new User(id, name);
    }
}
