package hibernate.lesson4.service;

import hibernate.lesson4.dao.UserDAO;
import hibernate.lesson4.model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User registerUser(User user) throws Exception {
        if (user.getUserName().equals("") || user.getPassword().equals("") || user.getCountry().equals(""))
            throw new Exception(getClass().getSimpleName()+"-registerUser. Values can not be empty");
        if(userDAO.getLogAndPas(user.getUserName(), user.getPassword()) != null)
            throw new Exception(getClass().getSimpleName()+"-registerUser. User with user name: "+user.getUserName()+" is already registered.");
        return userDAO.save(user);
    }
}
