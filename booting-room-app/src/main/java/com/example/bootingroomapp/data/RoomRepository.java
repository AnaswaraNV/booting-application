package com.example.bootingroomapp.data;

import com.example.bootingroomapp.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
