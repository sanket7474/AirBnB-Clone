package com.example.airBnBClone.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
public class HotelSearchRequest {

    private String city;
    private int roomCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int pageNumber = 0;
    private int pageSize = 10;
}
