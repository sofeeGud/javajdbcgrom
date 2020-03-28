package hibernate.lesson4.demo;

import hibernate.lesson4.dao.HotelDAO;
import hibernate.lesson4.model.Hotel;

public class HotelDemo {
    public static void main(String[] args) {
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel1 = new Hotel();
        hotel1.setName("hotel11");
        hotel1.setCountry("country1");
        hotel1.setCity("city1");
        hotel1.setStreet("street1");
        hotelDAO.save(hotel1);
        Hotel hotel2 = new Hotel();
        hotel2.setName("hotel2");
        hotel2.setCountry("country2");
        hotel2.setCity("city2");
        hotel2.setStreet("street2");
        hotelDAO.save(hotel2);
        Hotel hotel3 = new Hotel();
        hotel3.setName("hotel3");
        hotel3.setCountry("country3");
        hotel3.setCity("city3");
        hotel3.setStreet("street3");
        hotelDAO.save(hotel3);

    }
}
