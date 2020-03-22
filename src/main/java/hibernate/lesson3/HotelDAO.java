package hibernate.lesson3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class HotelDAO {

    private SessionFactory sessionFactory;

    public void save(Hotel hotel) {

        Session session = null;
        Transaction tr = null;
        try {

            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(hotel);

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


    public void update(Hotel hotel) {

        Session session = null;
        Transaction tr = null;
        try {

            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(hotel);

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


    public void delete(Hotel hotel) {

        Session session = null;
        Transaction tr = null;
        try {

            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(hotel);

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

    public Hotel findById(long id) throws Exception {
        Session session = null;
        try {
            session = createSessionFactory().openSession();
            return (Hotel) session.createSQLQuery("SELECT * FROM HOTEL WHERE ID = :hotelId")
                    .setParameter("hotelId", id)
                    .addEntity(Hotel.class).getSingleResult();

        } catch (HibernateException e) {
            throw new Exception("findById failed" + e.getMessage());
        } finally {
            if (session != null)
                session.close();
        }
    }


    private SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
