package com.example.farm.animal;

import com.example.farm.product.Product;
import com.example.farm.product.ProductType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@DiscriminatorValue("COW")
public class Cow extends Animal {

    public Cow() {
        super();
    }

    public Cow(String registrationNumber) {
        super(registrationNumber);
    }

    @Override
    public Product produce() {
        // Корова даёт 8-12 литров молока
        double milkLiters = 8 + ThreadLocalRandom.current().nextDouble() * 4;
        // Округляем до 1 знака после запятой
        milkLiters = Math.round(milkLiters * 10.0) / 10.0;
        return new Product(this, ProductType.MILK, milkLiters);
    }
}