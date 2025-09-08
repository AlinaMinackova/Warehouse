package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Category;
import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Product;
import org.example.warehouse.repository.ProductRepositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductService {

    public final ProductRepositories productRepository;

    public Page<Product> findAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> filterProducts(Long manufacturerId, Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (manufacturerId != null && categoryId != null) {
            return productRepository.findByManufacturer_IdAndCategory_Id(manufacturerId, categoryId, pageable);
        } else if (manufacturerId != null) {
            return productRepository.findByManufacturer_Id(manufacturerId, pageable);
        } else if (categoryId != null) {
            return productRepository.findByCategory_Id(categoryId, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public void save(Product product, Manufacturer manufacturer, Category category, MultipartFile file) throws IOException {
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        if (!file.isEmpty()) {
            String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath();
            File uploadFile = new File(uploadDir, Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(uploadFile);

            // Сохраняем путь для Thymeleaf
            product.setImageUrl("/uploads/" + file.getOriginalFilename());
        }
        productRepository.save(product);
    }


    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
//
    public Product update(Long id, Product product, Manufacturer manufacturer, Category category, MultipartFile file) throws IOException {
        Product existingProduct = findById(id);

        // Обновляем базовые поля
        existingProduct.setName(product.getName());
        existingProduct.setLifeDays(product.getLifeDays());
        existingProduct.setWeight(product.getWeight());
        existingProduct.setComposition(product.getComposition());
        existingProduct.setManufacturer(manufacturer);
        existingProduct.setCategory(category);
        // Если выбран новый файл, обновляем картинку
        if (!file.isEmpty()) {
            String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath();
            File uploadFile = new File(uploadDir, Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(uploadFile);

            existingProduct.setImageUrl("/uploads/" + file.getOriginalFilename());
        }
        return productRepository.save(existingProduct);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
