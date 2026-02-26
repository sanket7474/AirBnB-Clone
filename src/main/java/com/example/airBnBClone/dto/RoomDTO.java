package com.example.airBnBClone.dto;

import com.example.airBnBClone.entities.Hotel;
import jakarta.persistence.*;

import java.math.BigDecimal;

public class RoomDTO {

    private Long id;
    private Hotel hotel;
    private String roomType;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private int totalCount;
    private int capacity;
}
