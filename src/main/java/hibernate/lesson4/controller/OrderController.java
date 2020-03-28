package hibernate.lesson4.controller;

import hibernate.lesson4.demo.Demo;
import hibernate.lesson4.model.UserType;
import hibernate.lesson4.service.OrderService;

import java.util.Date;

public class OrderController extends Controller{

    private OrderService orderService;

    public OrderController() throws Exception {
        orderService = new OrderService();
        authorization = Demo.auth;
    }

    public void bookRoom(long roomId, long userId, long hotelId, Date dateFrom, Date dateTo) throws Exception {
        isAccess(UserType.USER);
        orderService.bookRoom(roomId, userId, hotelId, dateFrom, dateTo);
    }

    public void cancelReservation(long roomId, long userId) throws Exception {
        isAccess(UserType.USER);
        orderService.cancelReservation(roomId, userId);
    }
}
