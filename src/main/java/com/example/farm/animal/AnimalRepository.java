package com.example.farm.animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    // Поиск по регистрационному номеру
    Optional<Animal> findByRegistrationNumber(String registrationNumber);

    // Подсчёт животных по типу
    long countByRegistrationNumberStartingWith(String prefix);

    // Поиск всех коров (по discriminator)
    List<Animal> findByRegistrationNumberStartingWith(String prefix);

    // Проверка существования номера
    boolean existsByRegistrationNumber(String registrationNumber);
}