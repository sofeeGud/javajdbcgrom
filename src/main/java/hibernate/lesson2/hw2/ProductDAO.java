package hibernate.lesson2.hw2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
            return (Product) session.createSQLQuery("SELECT * FROM PRODUCT WHERE ID = :productId")
                    .setParameter("productId", id)
                    .addEntity(Product.class).getSingleResult();

        } catch (HibernateException e) {
            throw new Exception("FindById failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();
        }
    }

    public List<Product> findByName(String name) throws Exception {
        Session session = null;
        try {
            session = createSessionFactory().openSession();
            return (List<Product>) session.createSQLQuery("SELECT * FROM PRODUCT WHERE NAME = :productName")
                    .setParameter("productName", name)
                    .addEntity(Product.class).list();

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
            return (List<Product>) session.createSQLQuery("SELECT * FROM PRODUCT WHERE NAME LIKE :productName")
                    .setParameter("productName", "%" + name + "%")
                    .addEntity(Product.class).list();

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
            return (List<Product>) session.createSQLQuery("SELECT * FROM PRODUCT WHERE PRICE BETWEEN :priceFrom AND :priceTo")
                    .setParameter("priceFrom", price - delta)
                    .setParameter("priceTo", price + delta)
                    .addEntity(Product.class).list();

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
            return (List<Product>) session.createSQLQuery("SELECT * FROM PRODUCT WHERE NAME LIKE :productName ORDER BY NAME")
                    .setParameter("productName", "%" + name + "%")
                    .addEntity(Product.class).list();

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
            return (List<Product>) session.createSQLQuery("SELECT * FROM PRODUCT WHERE NAME LIKE :productName ORDER BY NAME DESC")
                    .setParameter("productName", "%" + name + "%")
                    .addEntity(Product.class).list();

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
            return (List<Product>) session.createSQLQuery("SELECT * FROM PRODUCT WHERE PRICE BETWEEN :priceFrom AND :priceTo ORDER BY PRICE DESC")
                    .setParameter("priceFrom", price - delta)
                    .setParameter("priceTo", price + delta)
                    .addEntity(Product.class).list();

        } catch (HibernateException e) {
            throw new Exception("FindByPriceSortedDesc failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();

        }
    }
}
