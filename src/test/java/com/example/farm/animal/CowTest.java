package com.example.farm.animal;

import com.example.farm.product.Product;
import com.example.farm.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cow Unit Tests")
class CowTest {

    @Test
    @DisplayName("Cow should produce milk")
    void produce_shouldReturnMilk() {
        // Given
        Cow cow = new Cow("COW-001");

        // When
        Product product = cow.produce();

        // Then
        assertThat(product).isNotNull();
        assertThat(product.getProductType()).isEqualTo(ProductType.MILK);
        assertThat(product.getQuantity()).isBetween(8.0, 12.0);
        assertThat(product.getAnimal()).isEqualTo(cow);
    }

    @Test
    @DisplayName("Cow should produce different amounts each time")
    void produce_shouldReturnRandomAmount() {
        // Given
        Cow cow = new Cow("COW-001");

        // When: собираем молоко 100 раз
        double total = 0;
        for (int i = 0; i < 100; i++) {
            total += cow.produce().getQuantity();
        }
        double average = total / 100;

        // Then: среднее должно быть около 10 литров (между 8 и 12)
        assertThat(average).isBetween(9.0, 11.0);
    }
}