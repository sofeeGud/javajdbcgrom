package hibernate.lesson4.dao;

import hibernate.lesson4.model.Order;
import hibernate.lesson4.model.Room;
import hibernate.lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

public class OrderDAO extends DAO<Order> {

    @Override
    public String getTableName() {
        return "ORDERS";
    }

    @Override
    public Class<Order> getClassName() {
        return Order.class;
    }

    @Override
    public Order save(Order order) {
        return super.save(order);
    }

    @Override
    public Order update(Order order) {
        return super.update(order);
    }

    @Override
    public Order delete(Order order) {
        return super.delete(order);
    }

    @Override
    public Order findById(long id) throws Exception {
        return super.findById(id);
    }

    UserDAO userDAO = new UserDAO();
    RoomDAO roomDAO = new RoomDAO();
    HotelDAO hotelDAO = new HotelDAO();

    public void bookRoom(long roomId, long userId,Date dateFrom, Date dateTo) throws Exception {
        User user = new UserDAO().findById(userId);
        Room room = new RoomDAO().findById(roomId);
        double price = (room.getPrice()) * (countDayBooking(new RoomDAO().findById(roomId).getDateAvailableFrom(), dateTo));
        Order order = new Order();
        order.setRoom(room);
        order.setUserOrdered(user);
        order.setDateFrom(dateFrom);
        order.setDateTo(dateTo);
        order.setMoneyPaid(price);
        save(order);
    }

    private Long countDayBooking(Date from, Date to) {
        long time = to.getTime() - from.getTime();
        return (time / (24 * 60 * 60 * 1000));
    }

    public void cancelReservation(long roomId, long userId) throws Exception {
        Room room = new RoomDAO().findById(roomId);
        room.setDateAvailableFrom(new Date());
        delete(getOrderByRoomIdAndUserId(roomId, userId));
        roomDAO.update(room);
    }

    private Order getOrderByRoomIdAndUserId(long roomId, long userId) throws Exception {
        try (Session session = createSessionFactory().openSession()) {

            return (Order) session.createSQLQuery("SELECT * FROM FP_ORDER WHERE USER_ID = :userId AND ROOM_ID = :roomId")
                    .setParameter("userId", userId)
                    .setParameter("roomId", roomId)
                    .addEntity(Order.class).getSingleResult();

        } catch (HibernateException e) {
            throw new Exception(getClass().getSimpleName() + "-getOrderByRoomAndUser roomId: " + roomId + ", userId: " + userId + " failed. " + e.getMessage());
        }
    }

    public boolean validateOrder(long roomId, long userId, long hotelId) throws Exception {
        if ((userDAO.findById(userId) != null) || (roomDAO.findById(roomId) != null) ||
                ((hotelDAO.findById(hotelId).getId()) == (roomDAO.findById(roomId).getHotel().getId()))) {
            return true;
        }
        return false;
    }

}
