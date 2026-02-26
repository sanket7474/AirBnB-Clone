package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.HotelDTO;

public interface HotelService {

    public HotelDTO createNewHotel(HotelDTO hotel);

    public HotelDTO getHotelById(Long id);

    public HotelDTO updateHotel(Long id, HotelDTO hotel);
}
