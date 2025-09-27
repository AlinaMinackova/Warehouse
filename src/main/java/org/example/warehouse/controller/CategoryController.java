package org.example.warehouse.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Category;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;


    @GetMapping("/findAll")
    public String findAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String keyword,
                          Model model) {
        Page<Category> categoryPage;

        if (keyword != null && !keyword.isEmpty()) {
            categoryPage = categoryService.search(keyword, page, size);
        } else {
            categoryPage = categoryService.findAll(page, size);
        }
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("pag", categoryPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", categoryPage.getTotalElements());
        return "category/category_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "/category/category_add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute Category category,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "/category/category_add";
        }

        try {
            categoryService.save(category);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.category", e.getMessage());
            return "category/category_add";
        }
        return "redirect:/category/findAll";
    }

    // Форма редактирования
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "/category/category_edit";
    }

    // Обновление
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute Category category,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "/category/category_edit";
        }
        try {
            categoryService.update(id, category);
        } catch (IllegalArgumentException e) {
            result.rejectValue("name", "error.category", e.getMessage());
            return "category/category_edit";
        }
        return "redirect:/category/findAll";
    }

    // Удаление
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/category/findAll";
    }
}
