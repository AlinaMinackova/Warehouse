package org.example.warehouse.controller;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Category;
import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Product;
import org.example.warehouse.service.CategoryService;
import org.example.warehouse.service.ManufacturerService;
import org.example.warehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    // список продуктов
    @GetMapping("/findAll")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "12") int size,
                       @RequestParam(required = false) Long manufacturerId,
                       @RequestParam(required = false) Long categoryId,
                       Model model) {

        Page<Product> productPage = productService.filterProducts(manufacturerId, categoryId, page, size);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pag", productPage);

        // для выпадающих списков
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("categories", categoryService.findAll());

        // чтобы сохранять выбранные фильтры
        model.addAttribute("selectedManufacturer", manufacturerId);
        model.addAttribute("selectedCategory", categoryId);

        return "product/product_list";
    }

    // форма добавления
    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "product/product_add";
    }

    // сохранение нового продукта
    @PostMapping("/add")
    public String create(@ModelAttribute Product product,
                         @RequestParam Long manufacturerId,
                         @RequestParam Long categoryId,
                         @RequestParam("file") MultipartFile file) throws IOException {

        Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
        Category category = categoryService.findById(categoryId);

        productService.save(product, manufacturer, category, file);
        return "redirect:/product/findAll";


    }

    // форма редактирования
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "product/product_edit"; // шаблон редактирования
    }

    // обновление продукта
// Обновление продукта
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute Product product,
                         @RequestParam Long manufacturerId,
                         @RequestParam Long categoryId,
                         @RequestParam("file") MultipartFile file) throws IOException {


        // Обновляем производителя и категорию
        Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
        Category category = categoryService.findById(categoryId);

        productService.update(id, product, manufacturer, category, file);
        return "redirect:/product/findAll";
    }

    // удаление
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/product/findAll";
    }
}
