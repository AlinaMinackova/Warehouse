package org.example.warehouse.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название продукта обязательно")
    @Size(max = 255, message = "Название продукта не должно превышать 255 символов")
    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_manufacturer"))
    private Manufacturer manufacturer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

    @Size(max = 500, message = "URL изображения не должен превышать 500 символов")
    private String imageUrl;

    @NotNull(message = "Срок годности обязателен")
    @Positive(message = "Срок годности должен быть больше 0")
    private Integer lifeDays;

    @NotNull(message = "Вес обязателен")
    @Positive(message = "Вес должен быть больше 0")
    @Digits(integer = 8, fraction = 2, message = "Вес должен быть числом с максимум 8 целыми и 2 дробными знаками")
    private BigDecimal weight;

    @Size(max = 2000, message = "Состав не должен превышать 2000 символов")
    private String composition;
}