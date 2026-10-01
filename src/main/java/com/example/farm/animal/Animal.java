package com.example.farm.animal;

import com.example.farm.product.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "animal_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Animal() {
        this.createdAt = LocalDateTime.now();
    }

    public Animal(String registrationNumber) {
        this();
        this.registrationNumber = registrationNumber;
    }

    // Абстрактный метод — каждый наследник реализует свою логику
    public abstract Product produce();

    // Getters и setters
    public Long getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
