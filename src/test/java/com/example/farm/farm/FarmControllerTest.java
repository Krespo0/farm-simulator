package com.example.farm.farm;

import com.example.farm.animal.AnimalRepository;
import com.example.farm.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("FarmController Integration Tests")
class FarmControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("POST /api/farm/initialize should initialize farm")
    void initializeFarm_shouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(post("/api/farm/initialize"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ферма инициализирована: 10 коров и 20 кур добавлены"));
    }

    @Test
    @DisplayName("POST /api/farm/collect should collect products")
    void collectProducts_shouldReturnProducts() throws Exception {
        // Given
        mockMvc.perform(post("/api/farm/initialize"));

        // When & Then
        mockMvc.perform(post("/api/farm/collect"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(30));
    }

    @Test
    @DisplayName("GET /api/farm/stats should return statistics")
    void getStats_shouldReturnStats() throws Exception {
        // Given
        mockMvc.perform(post("/api/farm/initialize"));

        // When & Then
        mockMvc.perform(get("/api/farm/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cowCount").value(10))
                .andExpect(jsonPath("$.chickenCount").value(20))
                .andExpect(jsonPath("$.totalAnimals").value(30));
    }

    @Test
    @DisplayName("POST /api/farm/buy should add animals")
    void buyAnimals_shouldAddAnimals() throws Exception {
        // Given
        mockMvc.perform(post("/api/farm/initialize"));

        // When & Then
        mockMvc.perform(post("/api/farm/buy")
                        .param("cows", "1")
                        .param("chickens", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Куплено: 1 коров, 5 кур"));

        // Проверка статистики
        mockMvc.perform(get("/api/farm/stats"))
                .andExpect(jsonPath("$.cowCount").value(11))
                .andExpect(jsonPath("$.chickenCount").value(25));
    }
}