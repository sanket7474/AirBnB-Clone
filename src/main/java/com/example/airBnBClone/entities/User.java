package com.example.airBnBClone.entities;

import com.example.airBnBClone.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users") // "user" is a reserved keyword in many databases, so we use "users"
public class User extends BaseTableModel {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    //fetch type is set to EAGER to load the roles along with the user entity, so that we can easily access the roles when needed
    @ElementCollection(fetch = FetchType.EAGER)
    // Enumrated is used to specify that the list contains enum values and how they should be stored in the database
    //EnumType.STRING is used to store the enum values as strings in the database
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
}
