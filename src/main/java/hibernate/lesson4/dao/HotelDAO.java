package hibernate.lesson4.dao;

import hibernate.lesson4.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class HotelDAO extends DAO<Hotel> {

    @Override
    public String getTableName() {
        return "HOTEL";
    }

    @Override
    public Class<Hotel> getClassName() {
        return Hotel.class;
    }

    @Override
    public Hotel save(Hotel hotel) {
        return super.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        return super.update(hotel);
    }

    @Override
    public Hotel delete(Hotel hotel) {
        return super.delete(hotel);
    }

    @Override
    public Hotel findById(long id) throws Exception {
        return super.findById(id);
    }


    public List<Hotel> findHotelByName(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return (List<Hotel>) session.createSQLQuery("SELECT * FROM HOTEL WHERE NAME = :name")
                    .setParameter("name", name)
                    .addEntity(Hotel.class).list();

        } catch (HibernateException e) {
            throw new Exception(getClass().getSimpleName() + "-findHotelByName " + name + " failed. " + e.getMessage());
        }
    }

    public List<Hotel> findHotelByCity(String name) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return (List<Hotel>) session.createSQLQuery("SELECT * FROM HOTEL WHERE CITY = :name")
                    .setParameter("name", name)
                    .addEntity(Hotel.class).list();

        } catch (HibernateException e) {
            throw new Exception(getClass().getSimpleName() + "-findHotelByCity " + name + " failed. " + e.getMessage());
        }
    }
}
