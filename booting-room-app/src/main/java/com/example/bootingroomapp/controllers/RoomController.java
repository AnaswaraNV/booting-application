package com.example.bootingroomapp.controllers;

import com.example.bootingroomapp.models.Room;
import com.example.bootingroomapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

//    @GetMapping
//    public String getAllRooms(Model model) {
//        List<Room> rooms = roomService.getAllRooms();
//        model.addAttribute("rooms", rooms);
//        return "rooms";
//    }

}
