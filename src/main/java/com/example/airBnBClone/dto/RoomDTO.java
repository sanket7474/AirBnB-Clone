package com.example.airBnBClone.dto;

import com.example.airBnBClone.entities.Hotel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDTO {

    private Long id;
    private String roomType;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private int totalCount;
    private int capacity;
}
