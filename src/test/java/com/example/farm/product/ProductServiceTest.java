package com.example.farm.product;

import com.example.farm.animal.Animal;
import com.example.farm.animal.AnimalRepository;
import com.example.farm.animal.Cow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("ProductService Integration Tests")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AnimalRepository animalRepository;

    private Animal testCow;

    @BeforeEach
    void setUp() {
        // Агрессивная очистка для гарантии чистого состояния (как мы делали в Task Manager)
        productRepository.deleteAllInBatch();
        animalRepository.deleteAllInBatch();

        // Создаём тестовое животное для привязки продукции
        testCow = animalRepository.save(new Cow("TEST-COW-001"));
    }

    @Test
    @DisplayName("Should save and find products by type")
    void saveAndFindByType() {
        // Given
        productService.save(new Product(testCow, ProductType.MILK, 10.5));
        productService.save(new Product(testCow, ProductType.EGGS, 1.0));

        // When
        List<Product> milkProducts = productService.findByType(ProductType.MILK);

        // Then
        assertThat(milkProducts).hasSize(1);
        assertThat(milkProducts.get(0).getProductType()).isEqualTo(ProductType.MILK);
        assertThat(milkProducts.get(0).getQuantity()).isEqualTo(10.5);
    }

    @Test
    @DisplayName("Should calculate total milk and eggs correctly")
    void getTotalMilkAndEggs() {
        // Given
        productService.save(new Product(testCow, ProductType.MILK, 10.0));
        productService.save(new Product(testCow, ProductType.MILK, 5.5));
        productService.save(new Product(testCow, ProductType.EGGS, 2.0));

        // When & Then
        assertThat(productService.getTotalMilk()).isEqualTo(15.5);
        assertThat(productService.getTotalEggs()).isEqualTo(2.0);
    }

    @Test
    @DisplayName("Should find products by animal ID")
    void findByAnimalId() {
        // Given
        productService.save(new Product(testCow, ProductType.MILK, 8.0));

        // When
        List<Product> products = productService.findByAnimalId(testCow.getId());

        // Then
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getAnimal().getId()).isEqualTo(testCow.getId());
    }

    @Test
    @DisplayName("Should find products by date range")
    void findByDateRange() {
        // Given
        productService.save(new Product(testCow, ProductType.MILK, 12.0));
        LocalDateTime past = LocalDateTime.now().minusDays(2);
        LocalDateTime future = LocalDateTime.now().plusDays(2);

        // When
        List<Product> products = productService.findByDateRange(past, future);

        // Then
        assertThat(products).hasSize(1);
    }
}