package org.example.warehouse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "manufacturer")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название производителя обязательно")
    @Size(max = 255, message = "Название производителя не должно превышать 255 символов")
    @Column(nullable = false, length = 255)
    private String name;

    @NotBlank(message = "Название страны обязателено")
    @Size(max = 100, message = "Название страны не должно превышать 100 символов")
    @Column(nullable = false, length = 100)
    private String country;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Введите корректный email")
    @Size(max = 255, message = "Email не должен превышать 255 символов")
    @Column(unique = true, nullable = false, length = 255)
    private String email;
}