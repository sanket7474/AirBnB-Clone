package com.example.airBnBClone.dto;


import com.example.airBnBClone.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {

    private Long id;
    private int roomCount;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private BookingStatus status;
    private Set<GuestDTO> guests;
}
