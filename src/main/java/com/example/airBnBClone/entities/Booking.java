package com.example.airBnBClone.entities;

import com.example.airBnBClone.entities.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class Booking extends BaseTableModel {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    // The @ManyToOne annotation is used to indicate that there is a many-to-one relationship between the Booking entity and the Hotel entity.
    // This means that each booking is associated with one hotel, but a hotel can have multiple bookings.
    // The @JoinColumn annotation specifies the foreign key column (hotel_id)
    // in the Booking table that references the primary key of the Hotel table.
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;


    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int roomCount;

    @Column(nullable = false)
    private LocalDateTime checkInDate;
    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToMany
    @JoinTable(
            name = "booking_guests",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> guests;
}
