package com.example.airBnBClone.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hotel")
public class Hotel extends BaseTableModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String city;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    // The @Embedded annotation is used to indicate that the fields of the HotelContactInfo class should be embedded in the Hotel entity. ]
    // This allows us to store the contact information directly within the Hotel table in the database, rather than creating a separate table for it.
    @Embedded
    private HotelContactInfo contactInfo;

    // The @OneToMany annotation is used to indicate that there is a one-to-many relationship between the Hotel entity and the Room entity.
    // This means that each hotel can have multiple rooms, but each room is associated with only one hotel.
    // mappedBy = "hotel" indicates that the Room entity owns the relationship and that the hotel field
    // in the Room entity is the foreign key that references the Hotel entity.
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<Room> rooms;
}
