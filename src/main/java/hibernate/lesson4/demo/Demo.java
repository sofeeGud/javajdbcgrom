package hibernate.lesson4.demo;

import hibernate.lesson4.controller.HotelController;
import hibernate.lesson4.model.Authorization;
import hibernate.lesson4.model.Filter;
import hibernate.lesson4.model.Hotel;
import hibernate.lesson4.service.RoomService;

import java.util.Date;

public class Demo {
    public static Authorization auth;

    public static void main(String[] args) throws Exception {
        auth = new Authorization();
        auth.login("user3", "333");


        HotelController hotelController = new HotelController();
        Hotel hotel10 = new Hotel();
        hotel10.setName("hotel10");
        hotel10.setCountry("country10");
        hotel10.setCity("city10");
        hotel10.setStreet("street10");

        hotelController.deleteHotel(34);
        //auth.logout();
        hotelController.addHotel(hotel10);

        //OrderController orderController = new OrderController();
        //orderController.bookRoom(1576409565661L, 1575747311933L, 1575747517072L, new Date(1595947517072L));
        //orderController.cancelReservation(1576409565661L, 1575747311933L);

        /*RoomService roomService = new RoomService();
        Filter filter = new Filter();
        System.out.println(roomService.findRooms(filter));*/
    }

    }
