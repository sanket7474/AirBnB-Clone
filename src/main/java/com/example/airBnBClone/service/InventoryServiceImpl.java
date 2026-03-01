package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.HotelDTO;
import com.example.airBnBClone.dto.HotelSearchRequest;
import com.example.airBnBClone.entities.Hotel;
import com.example.airBnBClone.entities.Inventory;
import com.example.airBnBClone.entities.Room;
import com.example.airBnBClone.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void initializeInventoryForRoom(Room room) {
       LocalDate today = LocalDate.now();
       LocalDate endDate = today.plusYears(1);

       for(; !today.isAfter(endDate); today = today.plusDays(1)) {

           Inventory inventory = Inventory.builder()
                   .hotel(room.getHotel())
                   .room(room)
                   .bookedCount(0)
                   .city(room.getHotel().getCity())
                   .date(today)
                   .price(room.getBasePrice())
                   .surgeFactor(BigDecimal.ONE)
                   .totalCount(room.getTotalCount())
                   .closed(false)
                   .build();

           inventoryRepository.save(inventory);
       }


    }

    @Override
    public Page<HotelDTO> searchHotels(HotelSearchRequest searchRequest) {

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(), searchRequest.getPageSize());

        Page<Hotel> hotels = inventoryRepository.findHotelsWithAvailableInventory(
                searchRequest.getCity(),
                searchRequest.getCheckInDate(),
                searchRequest.getCheckOutDate(),
                searchRequest.getRoomCount(),
                (searchRequest.getCheckOutDate().toEpochDay() - searchRequest.getCheckInDate().toEpochDay())+1,
                pageable
        );
        return hotels.map((hotel) -> modelMapper.map(hotel, HotelDTO.class));
    }
}
