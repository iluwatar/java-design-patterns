package com.iluwatar.monolithic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Represents a Database in which Orders are stored.
 */
@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @ManyToOne // Relationship with User
    private User user;

    @ManyToOne // Relationship with Product
    private Products product;

    private Integer quantity;

    private Double totalPrice;
}
