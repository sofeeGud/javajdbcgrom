package hibernate.lesson4.demo;

import hibernate.lesson4.dao.HotelDAO;
import hibernate.lesson4.dao.RoomDAO;
import hibernate.lesson4.model.Room;

import java.util.Date;

public class RoomDemo {
    public static void main(String[] args) throws Exception {
        RoomDAO roomDAO = new RoomDAO();
        Room room1 = new Room();
        room1.setNumberOfGuests(4);
        room1.setPrice(560.90);
        room1.setBreakfastIncluded(0);
        room1.setPetsAllowed(1);
        room1.setDateAvailableFrom(new Date());
        room1.setHotel(new HotelDAO().findById(22));
        roomDAO.save(room1);

        Room room2 = new Room();
        room2.setNumberOfGuests(6);
        room2.setPrice(290);
        room2.setBreakfastIncluded(1);
        room2.setPetsAllowed(0);
        room2.setDateAvailableFrom(new Date());
        room2.setHotel(new HotelDAO().findById(23));
        roomDAO.save(room2);

        Room room3 = new Room();
        room3.setNumberOfGuests(2);
        room3.setPrice(463.25);
        room3.setBreakfastIncluded(0);
        room3.setPetsAllowed(1);
        room3.setDateAvailableFrom(new Date());
        room3.setHotel(new HotelDAO().findById(24));
        roomDAO.save(room3);



    }
}
