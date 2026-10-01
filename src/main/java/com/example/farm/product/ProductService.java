package com.example.farm.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Сохранение продукции
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Сохранение списка продукции
    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    // Получение всей продукции
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Получение продукции по типу
    @Transactional(readOnly = true)
    public List<Product> findByType(ProductType productType) {
        return productRepository.findByProductType(productType);
    }

    // Получение продукции по животному
    @Transactional(readOnly = true)
    public List<Product> findByAnimalId(Long animalId) {
        return productRepository.findByAnimalId(animalId);
    }

    // Получение продукции за период
    @Transactional(readOnly = true)
    public List<Product> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return productRepository.findAll().stream()
                .filter(p -> p.getCollectionDate().isAfter(start)
                        && p.getCollectionDate().isBefore(end))
                .toList();
    }

    // Общее количество молока
    @Transactional(readOnly = true)
    public double getTotalMilk() {
        return productRepository.sumQuantityByProductType(ProductType.MILK);
    }

    // Общее количество яиц
    @Transactional(readOnly = true)
    public double getTotalEggs() {
        return productRepository.sumQuantityByProductType(ProductType.EGGS);
    }

    // Количество молока за период
    @Transactional(readOnly = true)
    public double getMilkByDateRange(LocalDateTime start, LocalDateTime end) {
        return productRepository.sumQuantityByProductTypeAndDateRange(
                ProductType.MILK, start, end);
    }

    // Количество яиц за период
    @Transactional(readOnly = true)
    public double getEggsByDateRange(LocalDateTime start, LocalDateTime end) {
        return productRepository.sumQuantityByProductTypeAndDateRange(
                ProductType.EGGS, start, end);
    }

    // Подсчёт записей по типу
    @Transactional(readOnly = true)
    public long countByType(ProductType productType) {
        return productRepository.countByProductType(productType);
    }
}