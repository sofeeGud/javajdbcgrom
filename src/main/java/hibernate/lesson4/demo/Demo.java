package hibernate.lesson4.demo;

import hibernate.lesson4.controller.HotelController;
import hibernate.lesson4.controller.OrderController;
import hibernate.lesson4.dao.RoomDAO;
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

        //hotelController.deleteHotel(34);
        //auth.logout();
        //hotelController.addHotel(hotel10);

        OrderController orderController = new OrderController();
        //orderController.bookRoom(23, 4, 24, new Date(1596474826521L), new Date(1606474826521L));
       // orderController.cancelReservation(23, 4);

        RoomService roomService = new RoomService();
        Filter filter = new Filter();
       // filter.setCity("city10");
        //filter.setCountry("country10");
        RoomDAO roomDAO = new RoomDAO();
        System.out.println(roomDAO.findRooms(filter));
    }

    }
