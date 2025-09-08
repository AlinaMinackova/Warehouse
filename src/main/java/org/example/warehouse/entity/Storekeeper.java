package org.example.warehouse.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "storekeeper")
public class Storekeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(length = 100)
    private String middleName;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private LocalDate birthday;
}