package com.iluwatar.monolithic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Represents a User entity for the database to interact with the rest of the project.
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generated Auto-increment ID
    private Long id;

    private String name;

    @Column(unique = true) // Ensures unique emails
    private String email;

    private String password;
}