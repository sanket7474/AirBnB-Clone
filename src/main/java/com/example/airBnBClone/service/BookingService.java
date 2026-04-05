package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.BookingDTO;
import com.example.airBnBClone.dto.BookingRequest;

public interface BookingService {


    BookingDTO initBooking(BookingRequest bookingRequest);
}
