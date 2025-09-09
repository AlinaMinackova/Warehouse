package org.example.warehouse.controller;

import lombok.AllArgsConstructor;
import org.example.warehouse.entity.Manufacturer;
import org.example.warehouse.entity.Warehouse;
import org.example.warehouse.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/manufacturer")
@AllArgsConstructor
public class ManufacturerController {

    @Autowired
    private final ManufacturerService manufacturerService;

    @GetMapping("/findAll")
    public String findAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          @RequestParam(required = false) String keyword,
                          Model model) {
        Page<Manufacturer> manufacturerPage;
        if (keyword != null && !keyword.isEmpty()) {
            manufacturerPage = manufacturerService.search(keyword, page, size);
        } else {
            manufacturerPage = manufacturerService.findAll(page, size);
        }
        model.addAttribute("manufacturers", manufacturerPage.getContent());
        model.addAttribute("pag", manufacturerPage);
        model.addAttribute("keyword", keyword);
        return "manufacturer/manufacturer_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "/manufacturer/manufacturer_add"; // Название твоего HTML шаблона
    }

    @PostMapping("/add")
    public String addManufacturer(@ModelAttribute Manufacturer manufacturer, BindingResult result) {
        if (result.hasErrors()) {
            return "/manufacturer/manufacturer_add";
        }
        manufacturerService.save(manufacturer);
        return "redirect:/manufacturer/findAll"; // Перенаправление на список производителей
    }

    // Форма редактирования
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "/manufacturer/manufacturer_edit"; // тот же шаблон
    }

    // Обновление (PUT)
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Manufacturer manufacturer) {
        manufacturerService.update(id, manufacturer);
        return "redirect:/manufacturer/findAll";
    }

    // Удаление
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        manufacturerService.delete(id);
        return "redirect:/manufacturer/findAll";
    }
}
