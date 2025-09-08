package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WarehouseService {

    public final WarehouseRepository warehouseRepository;

    public void save(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    public Page<Warehouse> findAll(int page, int size) {
        return warehouseRepository.findAll(PageRequest.of(page, size));
    }

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public Page<Warehouse> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return warehouseRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword, pageable);
    }


    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public Warehouse update(Long id, Warehouse updated) {
        Warehouse existing = findById(id);
        existing.setName(updated.getName());
        existing.setAddress(updated.getAddress());
        return warehouseRepository.save(existing);
    }

    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }
}
