package com.example.farm.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Поиск по типу продукции
    List<Product> findByProductType(ProductType productType);

    // Поиск по животному
    List<Product> findByAnimalId(Long animalId);

    // Поиск по типу и дате
    List<Product> findByProductTypeAndCollectionDateBetween(
            ProductType productType,
            LocalDateTime start,
            LocalDateTime end);

    // Подсчёт общего количества по типу
    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p WHERE p.productType = :productType")
    double sumQuantityByProductType(ProductType productType);

    // Подсчёт по типу за период
    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p " +
            "WHERE p.productType = :productType AND p.collectionDate BETWEEN :start AND :end")
    double sumQuantityByProductTypeAndDateRange(
            ProductType productType,
            LocalDateTime start,
            LocalDateTime end);

    // Подсчёт количества записей по типу
    long countByProductType(ProductType productType);
}