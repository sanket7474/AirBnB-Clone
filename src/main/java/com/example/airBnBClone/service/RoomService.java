package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.RoomDTO;
import com.example.airBnBClone.entities.Room;

import java.util.List;

public interface RoomService {

    public RoomDTO createNewRoom(Long hotelId, RoomDTO room);

    List<RoomDTO> getAllRoomsByHotelId(Long hotelId);

    RoomDTO getRoomById(Long id);

    void deleteRoomById(Long id);


}
