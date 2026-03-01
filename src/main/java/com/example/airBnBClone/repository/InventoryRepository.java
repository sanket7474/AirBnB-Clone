package com.example.airBnBClone.repository;

import com.example.airBnBClone.entities.Hotel;
import com.example.airBnBClone.entities.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
            SELECT DISTINCT i.hotel FROM Inventory i
            WHERE i.city = :city
            AND i.date BETWEEN :checkInDate AND :checkOutDate
            AND i.closed = false
            AND (i.totalCount - i.bookedCount) >= :roomCount
            GROUP BY i.hotel, i.room
            HAVING COUNT(i.date) = :dateCount
            """)
    Page<Hotel> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomCount") int roomCount,
            @Param("dateCount") long dateCount,
            Pageable pageable
            );
}
