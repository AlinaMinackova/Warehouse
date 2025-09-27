package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Category;
import org.example.warehouse.entity.Storekeeper;
import org.example.warehouse.repository.StorekeeperRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StorekeeperService {

    public final StorekeeperRepository storekeeperRepository;

    @Transactional()
    public void save(Storekeeper storekeeper) {
        if (storekeeperRepository.findByEmail(storekeeper.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Человек с таким email уже существует");
        }
        storekeeperRepository.save(storekeeper);
    }

    @Transactional(readOnly = true)
    public Page<Storekeeper> findAll(int page, int size) {
        return storekeeperRepository.findAllByOrderByLastNameAscFirstNameAscMiddleNameAsc(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public List<Storekeeper> findAll() {
        return storekeeperRepository.findAllByOrderByLastNameAscFirstNameAscMiddleNameAsc();
    }

    @Transactional(readOnly = true)
    public Page<Storekeeper> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastName").ascending());
        return storekeeperRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword, keyword, keyword, keyword, pageable
        );
    }

    @Transactional(readOnly = true)
    public Storekeeper findById(Long id) {
        return storekeeperRepository.findById(id).orElse(null);
    }

    @Transactional()
    public Storekeeper update(Long id, Storekeeper updated) {
        Storekeeper existing = findById(id);
        storekeeperRepository.findByEmail(updated.getEmail())
                .filter(s -> !s.getId().equals(id))
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Человек с таким email уже существует");
                });
        existing.setLastName(updated.getLastName());
        existing.setFirstName(updated.getFirstName());
        existing.setMiddleName(updated.getMiddleName());
        existing.setEmail(updated.getEmail());
        if (updated.getBirthday() == null){
            existing.setBirthday(existing.getBirthday());
        }
        else {
            existing.setBirthday(updated.getBirthday());
        }
        return storekeeperRepository.save(existing);
    }

    @Transactional()
    public void delete(Long id) {
        storekeeperRepository.deleteById(id);
    }
}
