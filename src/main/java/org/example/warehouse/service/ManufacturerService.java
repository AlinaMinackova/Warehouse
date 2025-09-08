package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.repository.ManufacturerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManufacturerService {

    public final ManufacturerRepository manufacturerRepository;

    public Page<Manufacturer> findAll(int page, int size) {
        return manufacturerRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Manufacturer> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return manufacturerRepository
                .findByNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword, keyword, keyword, pageable
                );
    }

    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    public void save(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }


    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    public Manufacturer update(Long id, Manufacturer updated) {
        Manufacturer existing = findById(id);
        existing.setName(updated.getName());
        existing.setCountry(updated.getCountry());
        existing.setEmail(updated.getEmail());
        return manufacturerRepository.save(existing);
    }

    public void delete(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
