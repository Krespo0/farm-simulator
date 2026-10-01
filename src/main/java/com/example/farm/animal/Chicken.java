package com.example.farm.animal;

import com.example.farm.product.Product;
import com.example.farm.product.ProductType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@DiscriminatorValue("CHICKEN")
public class Chicken extends Animal {

    public Chicken() {
        super();
    }

    public Chicken(String registrationNumber) {
        super(registrationNumber);
    }

    @Override
    public Product produce() {
        // Курица несёт 0 или 1 яйцо
        int eggs = ThreadLocalRandom.current().nextInt(2); // 0 или 1
        return new Product(this, ProductType.EGGS, eggs);
    }
}