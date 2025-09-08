package org.example.warehouse.repository;

import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositories extends JpaRepository<Product, Long> {

        Page<Product> findByManufacturer_Id(Long manufacturerId, Pageable pageable);

        Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);

        Page<Product> findByManufacturer_IdAndCategory_Id(Long manufacturerId, Long categoryId, Pageable pageable);

}
