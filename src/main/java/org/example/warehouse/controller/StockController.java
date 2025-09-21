package org.example.warehouse.controller;

import jakarta.validation.Valid;
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
import org.springframework.validation.BindingResult;
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
                       @RequestParam(defaultValue = "12") int size,
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
    public String create(@Valid @ModelAttribute Stock stock,
                         BindingResult result,
                         @RequestParam Long warehouseId,
                         @RequestParam Long productId,
                         @RequestParam Long storekeeperId,
                         Model model) {

        if (warehouseId == null) {
            result.rejectValue("warehouse", "NotNull", "Выберите склад");
        }
        if (productId == null) {
            result.rejectValue("product", "NotNull", "Выберите продукт");
        }
        if (storekeeperId == null) {
            result.rejectValue("storekeeper", "NotNull", "Выберите кладовщика");
        }

        stock.setWarehouse(warehouseService.findById(warehouseId));
        stock.setProduct(productService.findById(productId));
        stock.setStorekeeper(storekeeperService.findById(storekeeperId));

        if (result.hasErrors()) {
            model.addAttribute("stock", stock);
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("storekeepers", storekeeperService.findAll());
            return "stock/stock_add";
        }

        stockService.save(stock);
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
                         @Valid @ModelAttribute Stock stock,
                         BindingResult result,
                         @RequestParam Long warehouseId,
                         @RequestParam Long productId,
                         @RequestParam Long storekeeperId,
                         Model model) {

        if (warehouseId == null) {
            result.rejectValue("warehouse", "NotNull", "Выберите склад");
        }
        if (productId == null) {
            result.rejectValue("product", "NotNull", "Выберите продукт");
        }
        if (storekeeperId == null) {
            result.rejectValue("storekeeper", "NotNull", "Выберите кладовщика");
        }

        stock.setWarehouse(warehouseService.findById(warehouseId));
        stock.setProduct(productService.findById(productId));
        stock.setStorekeeper(storekeeperService.findById(storekeeperId));


        if (result.hasErrors()) {
            model.addAttribute("stock", stock);
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("storekeepers", storekeeperService.findAll());
            return "stock/stock_edit";
        }

        stockService.update(id, stock);
        return "redirect:/stock/findAll";
    }

    // удаление
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        stockService.delete(id);
        return "redirect:/stock/findAll";
    }
}
