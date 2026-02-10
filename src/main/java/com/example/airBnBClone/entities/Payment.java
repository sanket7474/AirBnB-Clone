package com.example.airBnBClone.entities;

import com.example.airBnBClone.entities.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Payment extends BaseTableModel {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentId;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double amount;

    @Column(nullable = false)
    private PaymentStatus status;


}
