package hibernate.lesson4.model;

import hibernate.lesson4.dao.UserDAO;

public class Authorization {
    private boolean isLogin = false;
    private User userNow;
    UserDAO userDAO = new UserDAO();

    public Authorization() {
    }

    public void login(String userName, String password) throws Exception {
        User user = new UserDAO().getLogAndPas(userName, password);
        if(user == null)
            if (user.getUserName().equals(userName) || user.getPassword().equals(password)) {
                isLogin = true;
                userNow = user;
            }
        if (!isLogin)
            System.err.println("Incorrect login " + userName + " or password " + password + " in method login");
    }

    public void logout() {
        isLogin = false;
    }

    public boolean checkIn() {
        return isLogin;
    }

    public boolean isAdmin() {
        return userNow.getUserType() == UserType.ADMIN;
    }
}
