package org.example.warehouse.repository;

import org.example.warehouse.entity.Product;
import org.example.warehouse.entity.Storekeeper;
import org.example.warehouse.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Page<Warehouse> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            String name, String address, Pageable pageable);
    Optional<Warehouse> findByAddress(String address);

    Page<Warehouse> findAllByOrderByNameAsc(Pageable pageable);

    List<Warehouse> findAllByOrderByNameAsc();
}
