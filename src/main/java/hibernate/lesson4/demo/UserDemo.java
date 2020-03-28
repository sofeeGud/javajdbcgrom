package hibernate.lesson4.demo;

import hibernate.lesson4.dao.UserDAO;
import hibernate.lesson4.model.User;
import hibernate.lesson4.model.UserType;

public class UserDemo {
    public static void main(String[] args) throws Exception {
        UserDAO userDAO = new UserDAO();

        User user = new User();
        user.setUserName("user3");
        user.setPassword("333");
        user.setCountry("Ukraine");
        user.setUserType(UserType.USER);
        userDAO.save(user);
        //System.out.println(userDAO.findById(1));
        User admin = new User();
        admin.setUserName("admin1");
        admin.setPassword("5264");
        admin.setCountry("Ukraine");
        admin.setUserType(UserType.ADMIN);
        //userDAO.save(admin);
        //user.setUserType(UserType.ADMIN);
        //userDAO.update(userDAO.findById(1));
    }


}
