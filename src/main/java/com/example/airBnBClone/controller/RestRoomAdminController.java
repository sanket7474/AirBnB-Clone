package com.example.airBnBClone.controller;

import com.example.airBnBClone.dto.RoomDTO;
import com.example.airBnBClone.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
public class RestRoomAdminController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long hotelId) {
        log.info("Received request to create room");
        RoomDTO room = roomService.createNewRoom(hotelId, roomDTO);
        log.info("Room created successfully with ID: {}", room.getId());
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long roomId) {

        log.info("Received request to fetch room with ID: {}", roomId);
        RoomDTO room = roomService.getRoomById(roomId);
        log.info("Room fetched successfully with ID: {}", room.getId());
        return ResponseEntity.ok(room);

    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRoomsByHotelId(@PathVariable Long hotelId) {
        log.info("Received request to fetch all rooms for hotel with ID: {}", hotelId);
        List<RoomDTO> rooms = roomService.getAllRoomsByHotelId(hotelId);
        log.info("Fetched {} rooms for hotel with ID: {}", rooms.size(), hotelId);
        return ResponseEntity.ok(rooms);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId) {
        log.info("Received request to delete room with ID: {}", roomId);
        roomService.deleteRoomById(roomId);
        log.info("Room deleted successfully with ID: {}", roomId);
        return ResponseEntity.noContent().build();
    }



}
