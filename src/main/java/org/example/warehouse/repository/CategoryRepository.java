package org.example.warehouse.repository;

import org.example.warehouse.entity.Category;
import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContainingIgnoreCase(
            String name, Pageable pageable);

}