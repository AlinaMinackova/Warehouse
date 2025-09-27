package org.example.warehouse.repository;

import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepositories extends JpaRepository<Product, Long> {

        Page<Product> findByManufacturer_Id(Long manufacturerId, Pageable pageable);

        Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);

        Page<Product> findByManufacturer_IdAndCategory_Id(Long manufacturerId, Long categoryId, Pageable pageable);

        Optional<Product> findByName(String name);

        Page<Product> findAllByOrderByNameAsc(Pageable pageable);

        List<Product> findAllByOrderByNameAsc();

}
