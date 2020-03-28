package hibernate.lesson4.controller;

import hibernate.lesson4.demo.Demo;
import hibernate.lesson4.model.Filter;
import hibernate.lesson4.model.Room;
import hibernate.lesson4.model.UserType;
import hibernate.lesson4.service.RoomService;

import java.util.List;

public class RoomController extends Controller {

    private RoomService roomService;

    public RoomController(){
        roomService = new RoomService();
        authorization = Demo.auth;
    }

    public Room addRoom(Room room) throws Exception {
        if (isAccess(UserType.ADMIN));
        return roomService.addRoom(room);
    }

    public void deleteRoom(long roomId) throws Exception {
        if (isAccess(UserType.ADMIN));
        roomService.deleteRoom(roomId);
    }

    public List<Room> findRooms(Filter filter) throws Exception {
        return roomService.findRooms(filter);
    }
}
