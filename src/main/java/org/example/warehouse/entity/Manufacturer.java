package org.example.warehouse.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "manufacturer")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 100)
    private String country;

    @Column(unique = true, nullable = false, length = 255)
    private String email;
}