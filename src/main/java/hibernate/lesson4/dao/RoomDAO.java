package hibernate.lesson4.dao;

import hibernate.lesson4.model.Filter;
import hibernate.lesson4.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class RoomDAO extends DAO<Room>{

    @Override
    public String getTableName() {
        return "ROOM";
    }

    @Override
    public Class<Room> getClassName() {
        return Room.class;
    }

    @Override
    public Room save(Room room) {
        return super.save(room);
    }

    @Override
    public Room update(Room room) {
        return super.update(room);
    }

    @Override
    public Room delete(Room room) {
        return super.delete(room);
    }

    @Override
    public Room findById(long id) throws Exception {
        return super.findById(id);
    }

    public List<Room> findRooms(Filter filter) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return (List<Room>) session.createSQLQuery("SELECT r.* FROM ROOM " +
                    "    LEFT JOIN HOTEL h ON r.HOTEL_ID = h.ID " +
                    "    WHERE (:NumberOfGuests = 0 OR r.NUMBER_OF_GUESTS = :numberOfGuests) " +
                    "    AND (:price = 0 OR r.PRICE <= :price) " +
                    "    AND (:breakfastIncluded = r.BREAKFAST_INCLUDED) " +
                    "    AND (:petsAllowed = r.PETS_ALLOWED) " +
                    "    AND (:dateAvailableFrom >= r.DATE_AVAILABLE_FROM) " +
                    "    AND (:hotelName IS NULL OR h.NAME = :hotelName) " +
                    "    AND (:hotelCountry IS NULL OR h.COUNTRY = :hotelCountry) " +
                    "    AND (:hotelCity IS NULL OR h.CITY = :hotelCity) " )
                    .setParameter("NumberOfGuests", filter.getNumberOfGuests())
                    .setParameter("price", filter.getPrice())
                    .setParameter("breakfastIncluded", filter.getBreakfastIncluded() ? 1 : 0)
                    .setParameter("petsAllowed", filter.getPetsAllowed() ? 1 : 0)
                    .setParameter("dateAvailableFrom", filter.getDateAvailableFrom())
                    .setParameter("hotelName", filter.getName())
                    .setParameter("hotelCountry", filter.getCountry())
                    .setParameter("hotelCity", filter.getCity())
                    .addEntity(Room.class).list();

        } catch (HibernateException e) {
            throw new Exception(getClass().getSimpleName()+"-findRooms failed. "+e.getMessage());
        }
    }

}
