package com.example.airBnBClone.repository;

import com.example.airBnBClone.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {


}
