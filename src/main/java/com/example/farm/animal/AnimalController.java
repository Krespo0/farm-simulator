package com.example.farm.animal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // Получить всех животных
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.findAll();
    }

    // Получить животное по ID
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) {
        return animalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Добавить корову
    @PostMapping("/cows")
    public Cow addCow() {
        return animalService.addCow();
    }

    // Добавить курицу
    @PostMapping("/chickens")
    public Chicken addChicken() {
        return animalService.addChicken();
    }

    // Удалить животное
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Подсчёт коров
    @GetMapping("/count/cows")
    public long countCows() {
        return animalService.countCows();
    }

    // Подсчёт кур
    @GetMapping("/count/chickens")
    public long countChickens() {
        return animalService.countChickens();
    }
}