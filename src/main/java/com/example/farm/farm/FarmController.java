package com.example.farm.farm;

import com.example.farm.product.Product;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/farm")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    // Инициализация фермы (10 коров, 20 кур)
    @PostMapping("/initialize")
    public String initializeFarm() {
        farmService.initializeFarm();
        return "Ферма инициализирована: 10 коров и 20 кур добавлены";
    }

    // Сбор продукции у всех животных
    @PostMapping("/collect")
    public List<Product> collectProducts() {
        return farmService.collectAllProducts();
    }

    // Общая статистика
    @GetMapping("/stats")
    public FarmStats getStats() {
        return farmService.getStats();
    }

    // Статистика за период
    @GetMapping("/stats/period")
    public FarmStats getStatsForPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return farmService.getStatsForPeriod(start, end);
    }

    // Купить животных
    @PostMapping("/buy")
    public String buyAnimals(
            @RequestParam(defaultValue = "0") int cows,
            @RequestParam(defaultValue = "0") int chickens) {
        farmService.buyAnimals(cows, chickens);
        return String.format("Куплено: %d коров, %d кур", cows, chickens);
    }
}