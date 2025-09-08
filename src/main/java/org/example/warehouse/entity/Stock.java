package org.example.warehouse.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Ссылка на склад
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // 🔹 Ссылка на продукт
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 🔹 Количество
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    // 🔹 Дата поступления
    @Column(name = "arrival_date", nullable = false)
    private LocalDateTime arrivalDate = LocalDateTime.now();

    // 🔹 Ссылка на кладовщика
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storekeeper_id", nullable = false)
    private Storekeeper storekeeper;
}
