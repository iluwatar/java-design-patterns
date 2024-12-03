package com.iluwatar.monolithic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Represents a databse of products .
 */
@Entity
@Data
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generated Auto-increment ID
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;
}
