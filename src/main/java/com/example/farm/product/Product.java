package com.example.farm.product;

import com.example.farm.animal.Animal;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Column(nullable = false)
    private double quantity;

    @Column(name = "collection_date", nullable = false)
    private LocalDateTime collectionDate;

    public Product() {
        this.collectionDate = LocalDateTime.now();
    }

    public Product(Animal animal, ProductType productType, double quantity) {
        this();
        this.animal = animal;
        this.productType = productType;
        this.quantity = quantity;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public ProductType getProductType() {
        return productType;
    }

    public double getQuantity() {
        return quantity;
    }

    public LocalDateTime getCollectionDate() {
        return collectionDate;
    }
}