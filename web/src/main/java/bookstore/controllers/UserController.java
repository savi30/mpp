package bookstore.controllers;

import bookstore.domain.user.User;
import bookstore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return (List<User>) userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getById(@PathVariable String id) {
        return userService.findById(id).get();
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public List<User> getBooksByAuthor(@PathVariable String username) {
        return (List<User>) userService.filterUsersByName(username);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveUser(@RequestBody User user) {
        userService.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

}

