package org.example.warehouse.repository;

import org.example.warehouse.entity.Storekeeper;
import org.example.warehouse.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Page<Warehouse> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            String name, String address, Pageable pageable);
    Optional<Warehouse> findByAddress(String address);
}
