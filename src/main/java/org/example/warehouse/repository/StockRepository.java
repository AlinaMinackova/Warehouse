package org.example.warehouse.repository;

import org.example.warehouse.entity.Product;
import org.example.warehouse.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Page<Stock> findByWarehouse_Id(Long manufacturerId, Pageable pageable);

    Page<Stock> findByProduct_Id(Long productId, Pageable pageable);

    Page<Stock> findByWarehouse_IdAndProduct_Id(Long manufacturerId, Long categoryId, Pageable pageable);

}
