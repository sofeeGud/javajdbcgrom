package hibernate.lesson4.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public abstract class DAO <T>{

    private SessionFactory sessionFactory;

    public abstract String getTableName();
    public abstract Class<T> getClassName();

    public T save(T t) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){


            tr = session.getTransaction();
            tr.begin();

            session.save(t);

            tr.commit();
        } catch (HibernateException e) {
            System.out.println("Save is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("Save is done");
        return t;
    }


    public T update(T t) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){

            tr = session.getTransaction();
            tr.begin();

            session.update(t);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println("Update is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        }
        System.out.println("Update is done");
        return t;
    }

    public T delete(T t) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){

            tr = session.getTransaction();
            tr.begin();

            session.delete(t);

            tr.commit();
        } catch (HibernateException e) {
            System.out.println("Delete is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("Delete is done");
        return t;
    }



    public T findById(long id) throws Exception {

        try (Session session = createSessionFactory().openSession()) {
            return (T) session.createSQLQuery("SELECT * FROM " +  getTableName()+ " WHERE ID = :hotelId")
                    .setParameter("hotelId", id)
                    .addEntity(getClassName()).getSingleResult();

        } catch (HibernateException e) {
            throw new Exception("findById failed" + e.getMessage());
        }
    }


    public SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
