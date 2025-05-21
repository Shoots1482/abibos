package com.example.demo.repositories;

import com.example.demo.entities.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryName(String name);
    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);
    boolean existsByCategoryName(String name);
    
    @Query("SELECT c FROM Category c WHERE SIZE(c.productCategories) > 0")
    List<Category> findByProductIsNotEmpty();
}
