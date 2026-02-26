package com.example.airBnBClone.dto;

import com.example.airBnBClone.entities.HotelContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class HotelDTO {

    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private List<RoomDTO> rooms;
    private boolean active;
}
