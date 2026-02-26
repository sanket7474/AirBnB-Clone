package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.HotelDTO;
import com.example.airBnBClone.entities.Hotel;
import com.example.airBnBClone.exception.ResourceNotFoundException;
import com.example.airBnBClone.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDTO createNewHotel(HotelDTO hotelDTO) {

        log.info("Creating new hotel: {}", hotelDTO.getName());

        Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);

        log.info("Hotel created with ID: {}", hotel.getId());

        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public HotelDTO getHotelById(Long id) {

        log.info("Fetching hotel with ID: {}", id);

        Hotel hotel = hotelRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        log.info("Hotel found: {}", hotel.getName());
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO hotel) {

        log.info("Updating hotel with ID: {}", id);

         Hotel existingHotel = hotelRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));

         modelMapper.map(hotel, existingHotel);
        existingHotel.setId(id);
        existingHotel = hotelRepository.save(existingHotel);
        log.info("Hotel updated successfully: {}", existingHotel.getName());

        return modelMapper.map(existingHotel, HotelDTO.class);
    }
}
