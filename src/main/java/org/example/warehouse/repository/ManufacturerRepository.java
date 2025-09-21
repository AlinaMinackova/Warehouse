package org.example.warehouse.repository;

import org.example.warehouse.entity.Category;
import org.example.warehouse.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Page<Manufacturer> findByNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String country, String email, Pageable pageable
    );
//    Optional<Manufacturer> findByNameAndEmail(String name, String email);
    Optional<Manufacturer> findByName(String name);

}