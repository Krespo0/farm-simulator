package com.example.farm.animal;

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
@DisplayName("AnimalService Integration Tests")
class AnimalServiceTest {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalRepository animalRepository;

    @BeforeEach
    void setUp() {
        animalRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("Should add cow with auto-generated registration number")
    void addCow_shouldGenerateRegistrationNumber() {
        // When
        Cow cow = animalService.addCow();

        // Then
        assertThat(cow).isNotNull();
        assertThat(cow.getId()).isNotNull();
        assertThat(cow.getRegistrationNumber()).isEqualTo("COW-001");
    }

    @Test
    @DisplayName("Should add chicken with auto-generated registration number")
    void addChicken_shouldGenerateRegistrationNumber() {
        // When
        Chicken chicken = animalService.addChicken();

        // Then
        assertThat(chicken).isNotNull();
        assertThat(chicken.getId()).isNotNull();
        assertThat(chicken.getRegistrationNumber()).isEqualTo("HEN-001");
    }

    @Test
    @DisplayName("Should generate unique registration numbers")
    void addMultipleAnimals_shouldGenerateUniqueNumbers() {
        // When
        Cow cow1 = animalService.addCow();
        Cow cow2 = animalService.addCow();
        Chicken hen1 = animalService.addChicken();

        // Then
        assertThat(cow1.getRegistrationNumber()).isEqualTo("COW-001");
        assertThat(cow2.getRegistrationNumber()).isEqualTo("COW-002");
        assertThat(hen1.getRegistrationNumber()).isEqualTo("HEN-001");
    }

    @Test
    @DisplayName("Should count cows correctly")
    void countCows_shouldReturnCorrectCount() {
        // Given
        animalService.addCow();
        animalService.addCow();
        animalService.addChicken();

        // When
        long count = animalService.countCows();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should count chickens correctly")
    void countChickens_shouldReturnCorrectCount() {
        // Given
        animalService.addCow();
        animalService.addChicken();
        animalService.addChicken();
        animalService.addChicken();

        // When
        long count = animalService.countChickens();

        // Then
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("Should find all animals")
    void findAll_shouldReturnAllAnimals() {
        // Given
        animalService.addCow();
        animalService.addChicken();

        // When
        List<Animal> animals = animalService.findAll();

        // Then
        assertThat(animals).hasSize(2);
    }
}