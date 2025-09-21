package org.example.warehouse.repository;

import org.example.warehouse.entity.Storekeeper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorekeeperRepository extends JpaRepository<Storekeeper, Long> {
    Page<Storekeeper> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String lastName, String firstName, String middleName, String email, Pageable pageable
    );
    Optional<Storekeeper> findByEmail(String email);


}