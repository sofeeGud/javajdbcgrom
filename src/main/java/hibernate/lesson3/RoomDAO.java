package hibernate.lesson3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class RoomDAO {

    private SessionFactory sessionFactory;

    public void save(Room room) {

        Transaction tr = null;
        try(Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();
            session.save(room);
            tr.commit();
            session.close();
        } catch (HibernateException e) {
            System.out.println("Save is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("Save is done");
    }


    public void update(Room room) {


        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){

            tr = session.getTransaction();
            tr.begin();

            session.update(room);

            tr.commit();

            session.close();
        } catch (HibernateException e) {
            System.out.println("Update is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("Update is done");
    }


    public void delete(Room room) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {


            tr = session.getTransaction();
            tr.begin();

            session.delete(room);

            tr.commit();
            session.close();
        } catch (HibernateException e) {
            System.out.println("Delete is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("Delete is done");
    }

    public Room findById(long id) throws Exception {
        try (Session session = createSessionFactory().openSession();) {

            return (Room) session.createSQLQuery("SELECT * FROM ROOM WHERE ID = :roomId")
                    .setParameter("roomId", id)
                    .addEntity(Room.class).getSingleResult();


        } catch (HibernateException e) {
            throw new Exception("findById failed" + e.getMessage());
        }
    }

    private SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
