package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WarehouseService {

    public final WarehouseRepository warehouseRepository;

    @Transactional()
    public void save(Warehouse warehouse) {
        if (warehouseRepository.findByAddress(warehouse.getAddress()).isPresent()) {
            throw new IllegalArgumentException("Склад с таким адресом уже существует");
        }
        warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = true)
    public Page<Warehouse> findAll(int page, int size) {
        return warehouseRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Warehouse> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return warehouseRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword, pageable);
    }

    @Transactional(readOnly = true)
    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @Transactional()
    public Warehouse update(Long id, Warehouse updated) {
        Warehouse existing = findById(id);
        warehouseRepository.findByAddress(updated.getAddress())
                .filter(w -> !w.getId().equals(id))
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Склад с таким адресом уже существует");
                });
        existing.setName(updated.getName());
        existing.setAddress(updated.getAddress());
        return warehouseRepository.save(existing);
    }

    @Transactional()
    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }
}
