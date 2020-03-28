package hibernate.lesson4.service;

import hibernate.lesson4.dao.HotelDAO;
import hibernate.lesson4.model.Hotel;

import java.util.List;

public class HotelService {

    private HotelDAO hotelDAO;

    public HotelService() {
        hotelDAO = new HotelDAO();
    }

    public Hotel addHotel(Hotel hotel) throws Exception{
        if(hotel.getName().equals("") || hotel.getCountry().equals("") || hotel.getCity().equals("") || hotel.getStreet().equals(""))
            throw new Exception(getClass().getSimpleName()+"-addHotel. Values can not be empty");
        return hotelDAO.save(hotel);
    }

    public void deleteHotel(long hotelId) throws Exception {
        hotelDAO.delete(hotelDAO.findById(hotelId));
    }

    public List<Hotel> findHotelByName(String name) throws Exception{
        if(name.equals(""))
            throw new Exception(getClass().getSimpleName()+"-findHotelByName. Name can not be empty");

        return hotelDAO.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String name) throws Exception {
        if(name.equals(""))
            throw new Exception(getClass().getSimpleName()+"-findHotelByCity. City can not be empty");

        return hotelDAO.findHotelByCity(name);
    }
}
