package com.example.airBnBClone.dto;

import com.example.airBnBClone.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private List<Role> roles;
}
