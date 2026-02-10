package com.example.airBnBClone.entities;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

// This class serves as a base model for all entities, providing common fields like createdAt and updatedAt.
// MappedSuperclass indicates that this class is not an entity itself but provides mapping information for its subclasses.
@MappedSuperclass
public class BaseTableModel {

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
