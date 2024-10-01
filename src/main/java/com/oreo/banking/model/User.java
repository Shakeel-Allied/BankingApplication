package com.oreo.banking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // For simplicity, roles are stored as a comma-separated string
    private String roles; // e.g., "ROLE_USER,ROLE_ADMIN"

    // Getters and Setters
    // ...
}

