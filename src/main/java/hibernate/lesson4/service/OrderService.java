package hibernate.lesson4.service;

import hibernate.lesson4.dao.OrderDAO;
import hibernate.lesson4.dao.RoomDAO;

import java.util.Date;

public class OrderService {
    private OrderDAO orderDAO;
    private RoomDAO roomDAO;

    public OrderService() {
        orderDAO = new OrderDAO();
        roomDAO = new RoomDAO();
    }
    public void bookRoom(long roomId, long userId, long hotelId, Date dateTo, Date dateFrom) throws Exception {
        if (!orderDAO.validateOrder(roomId, userId, hotelId)) {
            System.err.println("Failed to place an order. Check incoming parameters. In method bookRoom");
        } else
            orderDAO.bookRoom(roomId, userId, dateFrom, dateTo);
    }

    public void cancelReservation(long roomId, long userId) throws Exception {
        orderDAO.cancelReservation(roomId, userId);
    }


}
