package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.RoomDTO;
import com.example.airBnBClone.entities.Hotel;
import com.example.airBnBClone.entities.Room;
import com.example.airBnBClone.exception.ResourceNotFoundException;
import com.example.airBnBClone.repository.HotelRepository;
import com.example.airBnBClone.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public RoomDTO createNewRoom(Long hotelId, RoomDTO room) {

        log.info("Creating new room for hotel with id: {}", hotelId);

        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with id: " + hotelId + " not found"));

        Room roomEntity = modelMapper.map(room, Room.class);

        roomEntity.setHotel(hotel);
        roomEntity = roomRepository.save(roomEntity);

        if(hotel.isActive()) {
            inventoryService.initializeInventoryForRoom(roomEntity);
        }

        log.info("Room created successfully with id: {}", roomEntity.getId());

        return modelMapper.map(roomEntity, RoomDTO.class);
    }

    @Override
    public List<RoomDTO> getAllRoomsByHotelId(Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with id: " + hotelId + " not found"));

        return hotel.getRooms().stream()
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .toList();
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        log.info("Fetching room with id: {}", id);

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room with id: " + id + " not found"));
        log.info("Room found: {}", room.getId());
        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    public void deleteRoomById(Long id) {

        log.info("Deleting room with id: {}", id);

        boolean exists = roomRepository.existsById(id);

        if(!exists) {
            log.warn("Room with id: {} not found. Deletion aborted.", id);
            throw new ResourceNotFoundException("Room with id: " + id + " not found");
        }

        roomRepository.deleteById(id);

        log.info("Room deleted successfully with id: {}", id);
    }
}
