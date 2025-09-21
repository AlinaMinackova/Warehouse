package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.repository.ManufacturerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManufacturerService {

    public final ManufacturerRepository manufacturerRepository;

    @Transactional(readOnly = true)
    public Page<Manufacturer> findAll(int page, int size) {
        return manufacturerRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public Page<Manufacturer> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return manufacturerRepository
                .findByNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword, keyword, keyword, pageable
                );
    }

    @Transactional(readOnly = true)
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Transactional()
    public void save(Manufacturer manufacturer) {
        if (manufacturerRepository.findByName(manufacturer.getName()).isPresent()) {
            throw new IllegalArgumentException("Производитель с таким названием уже существует");
        }
        manufacturerRepository.save(manufacturer);
    }

    @Transactional(readOnly = true)
    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    @Transactional()
    public Manufacturer update(Long id, Manufacturer updated) {
        Manufacturer existing = findById(id);
        manufacturerRepository.findByName(updated.getName())
                .filter(m -> !m.getId().equals(id))
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Производитель с таким названием уже существует");
                });
        existing.setName(updated.getName());
        existing.setCountry(updated.getCountry());
        existing.setEmail(updated.getEmail());
        return manufacturerRepository.save(existing);
    }

    @Transactional()
    public void delete(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
