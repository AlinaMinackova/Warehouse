package org.example.warehouse.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Storekeeper;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.service.StorekeeperService;
import org.example.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/storekeeper")
@AllArgsConstructor
public class StorekeeperController {

    @Autowired
    private final StorekeeperService storekeeperService;

    @GetMapping("/findAll")
    public String findAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String keyword,
                          Model model) {

        Page<Storekeeper> storekeeperPage;
        if (keyword != null && !keyword.isEmpty()) {
            storekeeperPage = storekeeperService.search(keyword, page, size);
        } else {
            storekeeperPage = storekeeperService.findAll(page, size);
        }
        model.addAttribute("storekeepers", storekeeperPage.getContent());
        model.addAttribute("pag", storekeeperPage);
        model.addAttribute("keyword", keyword);
        return "storekeeper/storekeeper_list";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("storekeeper", new Storekeeper());
        return "/storekeeper/storekeeper_add"; // Название твоего HTML шаблона
    }

    @PostMapping("/add")
    public String addManufacturer(@Valid @ModelAttribute Storekeeper storekeeper, BindingResult result) {
        if (result.hasErrors()) {
            return "/storekeeper/storekeeper_add";
        }
        try {
            storekeeperService.save(storekeeper);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.storekeeper", e.getMessage());
            return "/storekeeper/storekeeper_add";
        }
        return "redirect:/storekeeper/findAll"; // Перенаправление на список производителей
    }

    // Форма редактирования
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Storekeeper storekeeper = storekeeperService.findById(id);
        model.addAttribute("storekeeper", storekeeper);
        return "/storekeeper/storekeeper_edit"; // тот же шаблон
    }

    // Обновление (PUT)
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Storekeeper storekeeper, BindingResult result) {
        if (result.hasErrors()) {
            return "/storekeeper/storekeeper_edit";
        }
        try {
            storekeeperService.update(id, storekeeper);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.storekeeper", e.getMessage());
            return "/storekeeper/storekeeper_edit";
        }
        return "redirect:/storekeeper/findAll";
    }

    // Удаление
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        storekeeperService.delete(id);
        return "redirect:/storekeeper/findAll";
    }
}
