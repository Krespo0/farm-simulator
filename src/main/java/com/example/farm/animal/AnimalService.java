package com.example.farm.animal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    // Добавление коровы с автоматической генерацией номера
    public Cow addCow() {
        String registrationNumber = generateRegistrationNumber("COW");
        Cow cow = new Cow(registrationNumber);
        return animalRepository.save(cow);
    }

    // Добавление курицы с автоматической генерацией номера
    public Chicken addChicken() {
        String registrationNumber = generateRegistrationNumber("HEN");
        Chicken chicken = new Chicken(registrationNumber);
        return animalRepository.save(chicken);
    }

    // Получение всех животных
    @Transactional(readOnly = true)
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    // Получение животного по ID
    @Transactional(readOnly = true)
    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    // Удаление животного
    public void deleteById(Long id) {
        animalRepository.deleteById(id);
    }

    // Подсчёт количества коров
    @Transactional(readOnly = true)
    public long countCows() {
        return animalRepository.findAll().stream()
                .filter(animal -> animal instanceof Cow)
                .count();
    }

    // Подсчёт количества кур
    @Transactional(readOnly = true)
    public long countChickens() {
        return animalRepository.findAll().stream()
                .filter(animal -> animal instanceof Chicken)
                .count();
    }

    // Генерация уникального регистрационного номера
    private String generateRegistrationNumber(String prefix) {
        long count;
        if (prefix.equals("COW")) {
            count = countCows();
        } else {
            count = countChickens();
        }

        String registrationNumber;
        do {
            count++;
            registrationNumber = String.format("%s-%03d", prefix, count);
        } while (animalRepository.existsByRegistrationNumber(registrationNumber));

        return registrationNumber;
    }
}