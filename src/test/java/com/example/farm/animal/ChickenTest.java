package com.example.farm.animal;

import com.example.farm.product.Product;
import com.example.farm.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Chicken Unit Tests")
class ChickenTest {

    @Test
    @DisplayName("Chicken should produce eggs")
    void produce_shouldReturnEggs() {
        // Given
        Chicken chicken = new Chicken("HEN-001");

        // When
        Product product = chicken.produce();

        // Then
        assertThat(product).isNotNull();
        assertThat(product.getProductType()).isEqualTo(ProductType.EGGS);
        assertThat(product.getQuantity()).isBetween(0.0, 1.0);
        assertThat(product.getAnimal()).isEqualTo(chicken);
    }

    @Test
    @DisplayName("Chicken should produce 0 or 1 eggs")
    void produce_shouldReturnZeroOrOneEgg() {
        // Given
        Chicken chicken = new Chicken("HEN-001");

        // When: собираем яйца 100 раз
        boolean hasZero = false;
        boolean hasOne = false;
        for (int i = 0; i < 100; i++) {
            double eggs = chicken.produce().getQuantity();
            if (eggs == 0.0) hasZero = true;
            if (eggs == 1.0) hasOne = true;
        }

        // Then: должны быть и 0, и 1 (вероятностно)
        assertThat(hasZero).isTrue();
        assertThat(hasOne).isTrue();
    }
}