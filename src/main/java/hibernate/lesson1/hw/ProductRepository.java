package hibernate.lesson1.hw;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ProductRepository {

    private SessionFactory sessionFactory;

    public void save(Product product) {

        Session session = null;
        Transaction tr = null;
        try {

            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(product);

            tr.commit();
        } catch (HibernateException e) {
            System.out.println("Save is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();

        }
        System.out.println("Save is done");
    }

    public SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public void update(Product product) {
        Session session = null;
        Transaction tr = null;
        try {

            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.update(product);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println("Update is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();

        }
        System.out.println("Update is done");
    }

    public void delete(long id) {
        Session session = null;
        Transaction tr = null;
        try {

            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Product product = session.load(Product.class, id);
            session.delete(product);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println("Delete is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();

        }
        System.out.println("Delete is done");
    }


}
