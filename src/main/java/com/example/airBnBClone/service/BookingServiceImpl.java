package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.BookingDTO;
import com.example.airBnBClone.dto.BookingRequest;
import com.example.airBnBClone.entities.*;
import com.example.airBnBClone.enums.BookingStatus;
import com.example.airBnBClone.repository.BookingRepository;
import com.example.airBnBClone.repository.HotelRepository;
import com.example.airBnBClone.repository.InventoryRepository;
import com.example.airBnBClone.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public BookingDTO initBooking(BookingRequest bookingRequest) {

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingRequest.getRoomId()));

        List<Inventory> inventories =  inventoryRepository.findAndLockAvailableInventoryForBooking(
                room.getId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount()
        );

        int totalDays = (int) bookingRequest.getCheckOutDate().toEpochDay() - (int) bookingRequest.getCheckInDate().toEpochDay();

        if(inventories.size() < totalDays) {
            log.warn("Not enough inventory available for booking request: {}", bookingRequest);
            throw new RuntimeException("Not enough inventory available for the selected dates");
        }

        for(Inventory inventory : inventories) {
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequest.getRoomsCount());
            inventory.setClosed(inventory.getBookedCount() >= inventory.getTotalCount());
        }
        inventoryRepository.saveAll(inventories);

        // Todo: Fetch the actual user from the security context or session
        User user = new User();
        user.setId(1L);


        Booking booking = Booking.builder()
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .roomCount(bookingRequest.getRoomsCount())
                .status(BookingStatus.RESERVED)
                .amount(BigDecimal.TEN)
                .user(user)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }
}
