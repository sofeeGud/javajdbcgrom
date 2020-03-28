package hibernate.lesson4.demo;

import hibernate.lesson4.dao.OrderDAO;

import java.util.Date;

public class OrderDemo {
    public static void main(String[] args) throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        //orderDAO.bookRoom(22, 1, new Date(), new Date(1595414476521L));
        orderDAO.bookRoom(21, 3, new Date(), new Date(1586474826521L));
        //orderDAO.bookRoom(23, 1, new Date(), new Date(159414476521L));
        //System.out.println(new Date(1585474826521L));
    }
}
