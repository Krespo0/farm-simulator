package com.example.farm.farm;

import com.example.farm.animal.AnimalRepository;
import com.example.farm.product.Product;
import com.example.farm.product.ProductRepository;
import com.example.farm.product.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("FarmService Integration Tests")
class FarmServiceTest {

    @Autowired
    private FarmService farmService;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAllInBatch();
        animalRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("Should initialize farm with 10 cows and 20 chickens")
    void initializeFarm_shouldAddCorrectAnimals() {
        // When
        farmService.initializeFarm();

        // Then
        FarmStats stats = farmService.getStats();
        assertThat(stats.getCowCount()).isEqualTo(10);
        assertThat(stats.getChickenCount()).isEqualTo(20);
        assertThat(stats.getTotalAnimals()).isEqualTo(30);
    }

    @Test
    @DisplayName("Should collect products from all animals")
    void collectAllProducts_shouldReturnProducts() {
        // Given
        farmService.initializeFarm();

        // When
        List<Product> products = farmService.collectAllProducts();

        // Then
        assertThat(products).hasSize(30); // 10 коров + 20 кур
    }

    @Test
    @DisplayName("Should collect milk from cows and eggs from chickens")
    void collectAllProducts_shouldCollectCorrectTypes() {
        // Given
        farmService.initializeFarm();

        // When
        List<Product> products = farmService.collectAllProducts();

        // Then
        long milkCount = products.stream()
                .filter(p -> p.getProductType() == ProductType.MILK)
                .count();
        long eggsCount = products.stream()
                .filter(p -> p.getProductType() == ProductType.EGGS)
                .count();

        assertThat(milkCount).isEqualTo(10);
        assertThat(eggsCount).isEqualTo(20);
    }

    @Test
    @DisplayName("Should calculate total milk correctly")
    void collectMultipleTimes_shouldAccumulateMilk() {
        // Given
        farmService.initializeFarm();

        // When: собираем 7 раз
        for (int i = 0; i < 7; i++) {
            farmService.collectAllProducts();
        }

        // Then
        FarmStats stats = farmService.getStats();
        // 10 коров × 7 раз × 8-12 литров = 560-840 литров
        assertThat(stats.getTotalMilkLiters()).isBetween(560.0, 840.0);
    }

    @Test
    @DisplayName("Should buy animals and update counts")
    void buyAnimals_shouldAddNewAnimals() {
        // Given
        farmService.initializeFarm();

        // When
        farmService.buyAnimals(1, 5);

        // Then
        FarmStats stats = farmService.getStats();
        assertThat(stats.getCowCount()).isEqualTo(11);
        assertThat(stats.getChickenCount()).isEqualTo(25);
        assertThat(stats.getTotalAnimals()).isEqualTo(36);
    }
}