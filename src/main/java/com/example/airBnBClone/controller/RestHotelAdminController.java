package com.example.airBnBClone.controller;

import com.example.airBnBClone.dto.HotelDTO;
import com.example.airBnBClone.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class RestHotelAdminController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody  HotelDTO hotelDTO) {
        log.info("Received request to create hotel: {}", hotelDTO.getName());
        HotelDTO createdHotel = hotelService.createNewHotel(hotelDTO);
        log.info("Hotel created successfully with ID: {}", createdHotel.getId());
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long hotelId) {
        log.info("Received request to fetch hotel with ID: {}", hotelId);
        HotelDTO hotelDTO = hotelService.getHotelById(hotelId);
        log.info("Hotel fetched successfully: {}", hotelDTO.getName());
        return new ResponseEntity<>(hotelDTO, HttpStatus.OK);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long hotelId, @RequestBody HotelDTO hotelDTO) {
        log.info("Received request to update hotel with ID: {}", hotelId);
        HotelDTO updatedHotel = hotelService.updateHotel(hotelId, hotelDTO);
        log.info("Hotel updated successfully: {}", updatedHotel.getName());
        return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
    }


}
