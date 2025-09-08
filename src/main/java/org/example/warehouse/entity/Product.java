package org.example.warehouse.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_manufacturer"))
    private Manufacturer manufacturer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

    @Size(max = 500)
    private String imageUrl;

    @Positive
    private Integer lifeDays;

    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal weight;

    private String composition;
}