package org.example.warehouse.controller;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.*;
import org.example.warehouse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {
    @Autowired
    private final StockService stockService;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final StorekeeperService storekeeperService;

    // список продуктов
    @GetMapping("/findAll")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) Long warehouseId,
                       @RequestParam(required = false) Long productId,
                       @RequestParam(defaultValue = "desc") String sort,
                       Model model) {

        Page<Stock> stockPage = stockService.filterProducts(warehouseId, productId, page, size, sort);

        model.addAttribute("stocks", stockPage.getContent());
        model.addAttribute("pag", stockPage);

        // для выпадающих списков
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("products", productService.findAll());

        // чтобы сохранять выбранные фильтры
        model.addAttribute("selectedWarehouse", warehouseId);
        model.addAttribute("selectedProduct", productId);
        model.addAttribute("selectedSort", sort);


        return "stock/stock_list";
    }

    // форма добавления
    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("stock", new Stock());
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("storekeepers", storekeeperService.findAll());
        return "stock/stock_add";
    }

//     сохранение нового продукта
    @PostMapping("/add")
    public String create(@ModelAttribute Stock stock,
                         @RequestParam Long warehouseId,
                         @RequestParam Long productId,
                         @RequestParam Long storekeeperId) {

        Warehouse warehouse = warehouseService.findById(warehouseId);
        Product product = productService.findById(productId);
        Storekeeper storekeeper = storekeeperService.findById(storekeeperId);

        stockService.save(stock, warehouse, product, storekeeper);
        return "redirect:/stock/findAll";


    }
//
//    // форма редактирования
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Stock stock = stockService.findById(id);
        model.addAttribute("stock", stock);
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("storekeepers", storekeeperService.findAll());;
        return "stock/stock_edit"; // шаблон редактирования
    }

    // обновление продукта
// Обновление продукта
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute Stock stock,
                         @RequestParam Long warehouseId,
                         @RequestParam Long productId,
                         @RequestParam Long storekeeperId) {


        // Обновляем производителя и категорию
        Warehouse warehouse = warehouseService.findById(warehouseId);
        Product product = productService.findById(productId);
        Storekeeper storekeeper = storekeeperService.findById(storekeeperId);

        stockService.update(id, stock, warehouse, product, storekeeper);
        return "redirect:/stock/findAll";
    }
//
//    // удаление
//    @GetMapping("/{id}/delete")
//    public String delete(@PathVariable Long id) {
//        productService.delete(id);
//        return "redirect:/product/findAll";
//    }
}
