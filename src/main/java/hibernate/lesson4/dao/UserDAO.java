package hibernate.lesson4.dao;

import hibernate.lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UserDAO extends DAO<User> {

    @Override
    public String getTableName() {
        return "USERS";
    }

    @Override
    public Class<User> getClassName() {
        return User.class;
    }

    @Override
    public User save(User user) {
        return super.save(user);
    }

    @Override
    public User update(User user) {
        return super.update(user);
    }

    @Override
    public User delete(User user) {
        return super.delete(user);
    }

    @Override
    public User findById(long id) throws Exception {
        return super.findById(id);
    }


    public User getLogAndPas(String userName, String password) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return (User) session.createSQLQuery("SELECT * FROM USERS WHERE USER_NAME = :userName AND PASSWORD = :password")
                    .setParameter("userName", userName)
                    .setParameter("password", password)
                    .addEntity(User.class).getSingleResult();

        } catch (HibernateException e) {
            throw new Exception(getClass().getSimpleName() + "-getLogAndPas " + userName + " failed. " + e.getMessage());
        }
    }
}
