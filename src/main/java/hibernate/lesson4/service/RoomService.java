package hibernate.lesson4.service;

import hibernate.lesson4.dao.RoomDAO;
import hibernate.lesson4.model.Filter;
import hibernate.lesson4.model.Room;

import java.util.List;

public class RoomService {
    private RoomDAO roomDAO;

    public RoomService() {
        roomDAO = new RoomDAO();
    }

    public Room addRoom(Room room) throws Exception {
        if(room.getNumberOfGuests() <= 0 || room.getPrice() <= 0 || room.getDateAvailableFrom() == null)
            throw new Exception(getClass().getSimpleName()+"-addRoom. Values can not be empty");

        return roomDAO.save(room);
    }

    public void deleteRoom(long roomId) throws Exception {
        roomDAO.delete(roomDAO.findById(roomId));
    }

    public List<Room> findRooms(Filter filter) throws Exception {
        return roomDAO.findRooms(filter);
    }
}
