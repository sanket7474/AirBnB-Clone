package com.example.airBnBClone.entities;

import com.example.airBnBClone.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users") // "user" is a reserved keyword in many databases, so we use "users"
public class User extends BaseTableModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String name;

    //fetch type is set to EAGER to load the roles along with the user entity, so that we can easily access the roles when needed
    @ElementCollection(fetch = FetchType.EAGER)
    // Enumrated is used to specify that the list contains enum values and how they should be stored in the database
    //EnumType.STRING is used to store the enum values as strings in the database
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
