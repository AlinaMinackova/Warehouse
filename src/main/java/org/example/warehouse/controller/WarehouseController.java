package org.example.warehouse.controller;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/warehouse")
@AllArgsConstructor
public class WarehouseController {

    @Autowired
    private final WarehouseService warehouseService;

    @GetMapping("/findAll")
    public String findAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          @RequestParam(required = false) String keyword,
                          Model model) {
        Page<Warehouse> warehousesPage;

        if (keyword != null && !keyword.isEmpty()) {
            warehousesPage = warehouseService.search(keyword, page, size);
        } else {
            warehousesPage = warehouseService.findAll(page, size);
        }

        model.addAttribute("warehouses", warehousesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", warehousesPage.getTotalPages());
        model.addAttribute("keyword", keyword); // чтобы в input осталось значение поиска

        return "/warehouse/warehouse_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        return "/warehouse/warehouse_add"; // Название твоего HTML шаблона
    }

    @PostMapping("/add")
    public String addManufacturer(@ModelAttribute Warehouse warehouse, BindingResult result) {
        if (result.hasErrors()) {
            return "/warehouse/warehouse_add";
        }
        warehouseService.save(warehouse);
        return "redirect:/warehouse/findAll"; // Перенаправление на список производителей
    }

    // Форма редактирования
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Warehouse warehouse = warehouseService.findById(id);
        model.addAttribute("warehouse", warehouse);
        return "/warehouse/warehouse_edit"; // тот же шаблон
    }

    // Обновление (PUT)
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Warehouse warehouse) {
        warehouseService.update(id, warehouse);
        return "redirect:/warehouse/findAll";
    }

    // Удаление
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return "redirect:/warehouse/findAll";
    }
}
