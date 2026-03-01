package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.HotelDTO;
import com.example.airBnBClone.dto.HotelSearchRequest;
import com.example.airBnBClone.entities.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeInventoryForRoom(Room room);
    Page<HotelDTO> searchHotels(HotelSearchRequest searchRequest);
}
