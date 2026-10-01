package com.example.farm;

import com.example.farm.farm.FarmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final FarmService farmService;

    public HomeController(FarmService farmService) {
        this.farmService = farmService;
    }

    // Главная страница со статистикой
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stats", farmService.getStats());
        return "index";
    }

    // Сбор продукции за один день
    @PostMapping("/collect")
    public String collectProducts() {
        farmService.collectAllProducts();
        return "redirect:/"; // Перенаправляем обратно на главную для обновления данных
    }

    // Покупка животных
    @PostMapping("/buy")
    public String buyAnimals(
            @RequestParam(defaultValue = "0") int cows,
            @RequestParam(defaultValue = "0") int chickens) {
        farmService.buyAnimals(cows, chickens);
        return "redirect:/";
    }

    // Инициализация фермы (сброс и добавление стартовых животных)
    @PostMapping("/initialize")
    public String initializeFarm() {
        farmService.initializeFarm();
        return "redirect:/";
    }
}