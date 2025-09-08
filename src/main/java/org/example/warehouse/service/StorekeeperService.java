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

import java.util.List;

@Service
@AllArgsConstructor
public class StorekeeperService {

    public final StorekeeperRepository storekeeperRepository;

    public void save(Storekeeper storekeeper) {
        storekeeperRepository.save(storekeeper);
    }

    public Page<Storekeeper> findAll(int page, int size) {
        return storekeeperRepository.findAll(PageRequest.of(page, size));
    }

    public List<Storekeeper> findAll() {
        return storekeeperRepository.findAll();
    }

    public Page<Storekeeper> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastName").ascending());
        return storekeeperRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword, keyword, keyword, keyword, pageable
        );
    }


    public Storekeeper findById(Long id) {
        return storekeeperRepository.findById(id).orElse(null);
    }

    public Storekeeper update(Long id, Storekeeper updated) {
        Storekeeper existing = findById(id);
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

    public void delete(Long id) {
        storekeeperRepository.deleteById(id);
    }
}
