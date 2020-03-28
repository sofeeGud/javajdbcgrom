package hibernate.lesson4.controller;

import hibernate.lesson4.demo.Demo;
import hibernate.lesson4.model.Hotel;
import hibernate.lesson4.model.UserType;
import hibernate.lesson4.service.HotelService;

import java.util.List;

public class HotelController extends Controller{

    private HotelService hotelService;

    public HotelController() {
        hotelService = new HotelService();
        authorization = Demo.auth;
    }

    public Hotel addHotel(Hotel hotel) throws Exception {
        isAccess(UserType.ADMIN);
        return hotelService.addHotel(hotel);
    }

    public void deleteHotel(long hotelid) throws Exception {
        isAccess(UserType.ADMIN);
        hotelService.deleteHotel(hotelid);
    }

    public List<Hotel> findHotelByName(String name) throws Exception {
        isAccess(UserType.USER);
        return hotelService.findHotelByName(name);

    }

    public List<Hotel> findHotelByCity(String city) throws Exception {
        isAccess(UserType.USER);
        return hotelService.findHotelByCity(city);
    }
}
