package com.example.bootingroomapp.service;

import com.example.bootingroomapp.models.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final List<Room> rooms = getRooms();

    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        for (long i = 0; i<10; i++) {
            rooms.add(new Room(i, "Room "+i, "Q"));
        }
        return rooms;
    }
}
