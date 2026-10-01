package com.example.farm.farm;

import com.example.farm.animal.Animal;
import com.example.farm.animal.AnimalRepository;
import com.example.farm.animal.AnimalService;
import com.example.farm.animal.Cow;
import com.example.farm.animal.Chicken;
import com.example.farm.product.Product;
import com.example.farm.product.ProductService;
import com.example.farm.product.ProductType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FarmService {

    private final AnimalService animalService;
    private final ProductService productService;
    private final AnimalRepository animalRepository;

    public FarmService(AnimalService animalService,
                       ProductService productService,
                       AnimalRepository animalRepository) {
        this.animalService = animalService;
        this.productService = productService;
        this.animalRepository = animalRepository;
    }

    // Инициализация фермы: 10 коров и 20 кур
    public void initializeFarm() {
        for (int i = 0; i < 10; i++) {
            animalService.addCow();
        }
        for (int i = 0; i < 20; i++) {
            animalService.addChicken();
        }
    }

    // Сбор продукции у всех животных
    // КЛЮЧЕВОЙ МОМЕНТ: мы НЕ знаем, с каким типом животного работаем!
    // Это полиморфизм в чистом виде.
    public List<Product> collectAllProducts() {
        List<Animal> animals = animalRepository.findAll();
        List<Product> products = new ArrayList<>();

        for (Animal animal : animals) {
            // Вызываем produce() — каждый тип животного знает, что производить
            Product product = animal.produce();
            products.add(product);
        }

        // Сохраняем всю продукцию в БД одним батчем
        return productService.saveAll(products);
    }

    // Статистика по животным
    @Transactional(readOnly = true)
    public FarmStats getStats() {
        long cowCount = animalService.countCows();
        long chickenCount = animalService.countChickens();
        double totalMilk = productService.getTotalMilk();
        double totalEggs = productService.getTotalEggs();

        return new FarmStats(cowCount, chickenCount, totalMilk, totalEggs);
    }

    // Статистика за период
    @Transactional(readOnly = true)
    public FarmStats getStatsForPeriod(LocalDateTime start, LocalDateTime end) {
        long cowCount = animalService.countCows();
        long chickenCount = animalService.countChickens();
        double totalMilk = productService.getMilkByDateRange(start, end);
        double totalEggs = productService.getEggsByDateRange(start, end);

        return new FarmStats(cowCount, chickenCount, totalMilk, totalEggs);
    }

    // Добавить животных (покупка на рынке)
    public void buyAnimals(int cows, int chickens) {
        for (int i = 0; i < cows; i++) {
            animalService.addCow();
        }
        for (int i = 0; i < chickens; i++) {
            animalService.addChicken();
        }
    }
}