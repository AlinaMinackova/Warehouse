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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductService {

    public final ProductRepositories productRepository;

    @Transactional(readOnly = true)
    public Page<Product> findAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
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

    private String uploadImage(MultipartFile image) {
        try {
            String uploadDir = new File("uploads").getAbsolutePath();
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
                    + "_" + Objects.requireNonNull(image.getOriginalFilename());

            File uploadFile = new File(dir, filename);
            image.transferTo(uploadFile);

            return "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла", e);
        }
    }

    @Transactional()
    public void save(Product product, MultipartFile file) throws IOException {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException("Продукт с таким названием уже существует");
        }
        if (!file.isEmpty()) {
            // Сохраняем путь для Thymeleaf
            product.setImageUrl(uploadImage(file));
        }
        else {
            product.setImageUrl("/default.jpg");
        }
        productRepository.save(product);
    }


    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional()
    public Product update(Long id, Product product, MultipartFile file) throws IOException {
        Product existingProduct = findById(id);
        productRepository.findByName(product.getName())
                .filter(m -> !m.getId().equals(id))
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Продукт с таким названием уже существует");
                });
        // Обновляем базовые поля
        existingProduct.setName(product.getName());
        existingProduct.setLifeDays(product.getLifeDays());
        existingProduct.setWeight(product.getWeight());
        existingProduct.setComposition(product.getComposition());
        // Если выбран новый файл, обновляем картинку
        if (!file.isEmpty()) {
            existingProduct.setImageUrl(uploadImage(file));
        }
        return productRepository.save(existingProduct);
    }

    @Transactional()
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
