package com.example.farm.product;

import com.example.farm.animal.Animal;
import com.example.farm.animal.AnimalRepository;
import com.example.farm.animal.Cow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("ProductController Integration Tests")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AnimalRepository animalRepository;

    private Animal testCow;

    @BeforeEach
    void setUp() {
        productRepository.deleteAllInBatch();
        animalRepository.deleteAllInBatch();

        testCow = animalRepository.save(new Cow("TEST-COW-002"));

        // Создаём тестовые данные
        productRepository.save(new Product(testCow, ProductType.MILK, 10.5));
        productRepository.save(new Product(testCow, ProductType.EGGS, 1.0));
    }

    @Test
    @DisplayName("GET /api/products should return all products")
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/products/type/MILK should return only milk products")
    void getProductsByType() throws Exception {
        mockMvc.perform(get("/api/products/type/MILK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].productType").value("MILK"))
                .andExpect(jsonPath("$[0].quantity").value(10.5));
    }

    @Test
    @DisplayName("GET /api/products/animal/{id} should return products for specific animal")
    void getProductsByAnimal() throws Exception {
        mockMvc.perform(get("/api/products/animal/" + testCow.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/products/total/milk should return total milk quantity")
    void getTotalMilk() throws Exception {
        mockMvc.perform(get("/api/products/total/milk"))
                .andExpect(status().isOk())
                .andExpect(content().string("10.5"));
    }

    @Test
    @DisplayName("GET /api/products/total/eggs should return total eggs quantity")
    void getTotalEggs() throws Exception {
        mockMvc.perform(get("/api/products/total/eggs"))
                .andExpect(status().isOk())
                .andExpect(content().string("1.0"));
    }
}