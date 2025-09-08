package org.example.warehouse.service;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.*;
import org.example.warehouse.repository.ProductRepositories;
import org.example.warehouse.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StockService {
    public final StockRepository stockRepository;

    public Page<Stock> findAll(int page, int size) {
        return stockRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Stock> filterProducts(Long warehouseId, Long productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (warehouseId != null && productId != null) {
            return stockRepository.findByWarehouse_IdAndProduct_Id(warehouseId, productId, pageable);
        } else if (warehouseId != null) {
            return stockRepository.findByWarehouse_Id(warehouseId, pageable);
        } else if (productId != null) {
            return stockRepository.findByProduct_Id(productId, pageable);
        } else {
            return stockRepository.findAll(pageable);
        }
    }

    public void save(Stock stock, Warehouse warehouse, Product product, Storekeeper storekeeper) {
        stock.setWarehouse(warehouse);
        stock.setProduct(product);
        stock.setStorekeeper(storekeeper);
        stockRepository.save(stock);
    }


    public Stock findById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    public void update(Long id, Stock stock, Warehouse warehouse, Product product, Storekeeper storekeeper) {
        Stock existingStock = findById(id);

        existingStock.setWarehouse(warehouse);
        existingStock.setProduct(product);
        existingStock.setQuantity(stock.getQuantity());
        existingStock.setStorekeeper(storekeeper);
        if (stock.getArrivalDate() == null){
            existingStock.setArrivalDate(existingStock.getArrivalDate());
        }
        else {
            existingStock.setArrivalDate(stock.getArrivalDate());
        }

        stockRepository.save(existingStock);
    }

    public void delete(Long id) {
        stockRepository.deleteById(id);
    }
}
