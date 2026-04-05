package com.example.airBnBClone.dto;

import com.example.airBnBClone.entities.User;
import com.example.airBnBClone.enums.Gender;
import lombok.Data;

@Data
public class GuestDTO {

    private Long id;
    private User user;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;
}
