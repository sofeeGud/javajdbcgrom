package hibernate.lesson2.hw1;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAO {
    private SessionFactory sessionFactory;

    public SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public Product findById(long id) throws Exception {
        Session session = null;
        try {

            session = createSessionFactory().openSession();

            return (Product) session.createQuery("FROM Product WHERE id = : productId")
                    .setParameter("productId", id).getSingleResult();

        } catch (HibernateException e) {
            throw new Exception("findById failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }

    public List<Product> findByName(String name) throws Exception {
        Session session = null;
        try {
            session = createSessionFactory().openSession();
            return (List<Product>) session.createQuery("FROM Product WHERE name = : productName")
                    .setParameter("productName", name).list();

        } catch (HibernateException e) {
            throw new Exception("FindByName failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }

    public List<Product> findByContainedName(String name) throws Exception {
        Session session = null;
        try {

            session = createSessionFactory().openSession();
            return (List<Product>) session.createQuery("FROM Product WHERE name LIKE CONCAT('%',: productName, '%')")
                    .setParameter("productName", name).list();

        } catch (HibernateException e) {
            throw new Exception("FindByContainedName failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }

    public List<Product> findByPrice(int price, int delta) throws Exception {
        Session session = null;
        try {

            session = createSessionFactory().openSession();
            return (List<Product>) session.createQuery("FROM Product WHERE price BETWEEN : priceFrom AND : priceTo")
                    .setParameter("priceFrom", price - delta)
                    .setParameter("priceTo", price + delta).list();

        } catch (HibernateException e) {
            throw new Exception("FindByPrice failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }

    public List<Product> findByNameSortedAsc(String name) throws Exception {
        Session session = null;
        try {

            session = createSessionFactory().openSession();
            return (List<Product>) session.createQuery("FROM Product WHERE name LIKE CONCAT('%',: productName, '%') ORDER BY name")
                    .setParameter("productName", name).list();

        } catch (HibernateException e) {
            throw new Exception("FindByNameSortedAsc failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }

    public List<Product> findByNameSortedDesc(String name) throws Exception {
        Session session = null;
        try {

            session = createSessionFactory().openSession();
            return (List<Product>) session.createQuery("FROM Product WHERE name LIKE CONCAT('%',: productName, '%') ORDER BY name DESC")
                    .setParameter("productName", name).list();

        } catch (HibernateException e) {
            throw new Exception("FindByNameSortedDesc failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }

    public List<Product> findByPriceSortedDesc(int price, int delta) throws Exception {
        Session session = null;
        try {

            session = createSessionFactory().openSession();
            return (List<Product>) session.createQuery("FROM Product WHERE price BETWEEN : priceFrom AND : priceTo ORDER BY price DESC")
                    .setParameter("priceFrom", price - delta)
                    .setParameter("priceTo", price + delta).list();

        } catch (HibernateException e) {
            throw new Exception("FindByPriceSortedDesc failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }

}
