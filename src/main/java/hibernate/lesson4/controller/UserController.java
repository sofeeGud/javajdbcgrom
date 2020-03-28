package hibernate.lesson4.controller;
import hibernate.lesson4.model.User;
import hibernate.lesson4.service.UserService;

public class UserController {

    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public User registerUser(User user) throws Exception {
        return userService.registerUser(user);
    }
}
