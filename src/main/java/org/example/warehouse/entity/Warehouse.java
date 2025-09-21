package org.example.warehouse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название склада не может быть пустым")
    @Size(max = 100, message = "Название склада не должно превышать 100 символов")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Адрес склада не может быть пустым")
    @Size(max = 255, message = "Адрес склада не должен превышать 255 символов")
    @Column(nullable = false, length = 255, unique = true)
    private String address;
}