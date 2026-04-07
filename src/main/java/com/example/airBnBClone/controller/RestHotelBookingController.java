package com.example.airBnBClone.controller;


import com.example.airBnBClone.dto.BookingDTO;
import com.example.airBnBClone.dto.BookingRequest;
import com.example.airBnBClone.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
@Slf4j
public class RestHotelBookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> initBooking(@RequestBody BookingRequest bookingRequest) {

        log.info("Received booking request: {}", bookingRequest);
        BookingDTO bookingDTO = bookingService.initBooking(bookingRequest);
        log.info("Booking created successfully: {}", bookingDTO);
        return ResponseEntity.ok(bookingDTO);

    }

}
