package org.example.warehouse.repository;

import org.example.warehouse.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Page<Manufacturer> findByNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String country, String email, Pageable pageable
    );

}