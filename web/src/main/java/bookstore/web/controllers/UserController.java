package bookstore.web.controllers;

import bookstore.core.domain.user.User;
import bookstore.core.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> get() {
        return (List<User>) userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable String id) {
        return userService.findById(id).get();
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public List<User> getByUsername(@PathVariable String username) {
        return (List<User>) userService.filterUsersByName(username);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody User user) {
        userService.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

}
