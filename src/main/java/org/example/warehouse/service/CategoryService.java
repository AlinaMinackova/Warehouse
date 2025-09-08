package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Category;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    public final CategoryRepository categoryRepository;

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Page<Category> findAll(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }


    public Page<Category> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category update(Long id, Category updated) {
        Category existing = findById(id);
        existing.setName(updated.getName());
        return categoryRepository.save(existing);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
