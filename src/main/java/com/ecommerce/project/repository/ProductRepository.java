package com.ecommerce.project.repository;

import com.ecommerce.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> findByName(@Param("name") String name);

    @Query(value = "SELECT * FROM Product WHERE price > :price", nativeQuery = true)
    List<Product> findExpensiveProducts(@Param("price") Double price);
}
