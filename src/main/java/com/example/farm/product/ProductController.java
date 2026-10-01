package com.example.farm.product;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Получить всю продукцию
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // Получить продукцию по типу
    @GetMapping("/type/{type}")
    public List<Product> getProductsByType(@PathVariable ProductType type) {
        return productService.findByType(type);
    }

    // Получить продукцию по животному
    @GetMapping("/animal/{animalId}")
    public List<Product> getProductsByAnimal(@PathVariable Long animalId) {
        return productService.findByAnimalId(animalId);
    }

    // Получить продукцию за период
    @GetMapping("/period")
    public List<Product> getProductsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return productService.findByDateRange(start, end);
    }

    // Общее количество молока
    @GetMapping("/total/milk")
    public double getTotalMilk() {
        return productService.getTotalMilk();
    }

    // Общее количество яиц
    @GetMapping("/total/eggs")
    public double getTotalEggs() {
        return productService.getTotalEggs();
    }
}