package hibernate.lesson3;

import java.util.Date;

public class Demo {
    public static void main(String[] args) throws Exception {
        HotelDAO hotelDAO = new HotelDAO();
        RoomDAO roomDAO = new RoomDAO();

        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel1");
        hotel1.setCountry("Ukraine");
        hotel1.setCity("Kiev");
        hotel1.setStreet("88888");

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hotel2");
        hotel2.setCountry("Ukraine");
        hotel2.setCity("Odessa");
        hotel2.setStreet("uuuu");

        Hotel hotel3 = new Hotel();
        hotel3.setName("Hotel3");
        hotel3.setCountry("Ukraine");
        hotel3.setCity("Dnepr");
        hotel3.setStreet("iiiii");

        Hotel hotel4 = new Hotel();
        hotel4.setName("Hotel4");
        hotel4.setCountry("Ukraine");
        hotel4.setCity("Lvov");
        hotel4.setStreet("2222");
       // hotelDAO.save(hotel4);
        //hotelDAO.delete(hotel2);
        //System.out.println(hotelDAO.findById(7));
        //hotelDAO.update(hotel1);

        Room room1 = new Room();
        room1.setNumberOfGuests(4);
        room1.setPrice(560.90);
        room1.setBreakfastIncluded(1);
        room1.setPetsAllowed(0);
        room1.setDateAvailableFrom(new Date());
        room1.setHotel(hotel3);

        Room room2 = new Room();
        room2.setNumberOfGuests(4);
        room2.setPrice(1000.65);
        room2.setBreakfastIncluded(1);
        room2.setPetsAllowed(0);
        room2.setDateAvailableFrom(new Date());
        room2.setHotel(hotel2);

        Room room3 = new Room();
        room3.setNumberOfGuests(5);
        room3.setPrice(1000.65);
        room3.setBreakfastIncluded(1);
        room3.setPetsAllowed(0);
        room3.setDateAvailableFrom(new Date());
        room3.setHotel(hotel4);
        //roomDAO.save(room3);
        //roomDAO.delete(room3);
        //System.out.println(roomDAO.findById(22));
        roomDAO.update(room3);

    }
}
