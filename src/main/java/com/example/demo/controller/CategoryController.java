package com.example.demo.controller;

import com.example.demo.entities.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Optional<Category> category = categoryService.findByCategoryName(name);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam String keyword) {
        List<Category> categories = categoryService.findByCategoryNameContainingIgnoreCase(keyword);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/with-products")
    public ResponseEntity<List<Category>> getCategoriesWithProducts() {
        List<Category> categories = categoryService.findByProductsIsNotEmpty();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/exists/name")
    public ResponseEntity<Boolean> checkCategoryExistsByName(@RequestParam String name) {
        boolean exists = categoryService.existsByCategoryName(name);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkCategoryExistsById(@PathVariable Integer id) {
        boolean exists = categoryService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCategoryCount() {
        long count = categoryService.count();
        return ResponseEntity.ok(count);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.save(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Category>> createCategories(@RequestBody List<Category> categories) {
        List<Category> createdCategories = categoryService.saveAll(categories);
        return new ResponseEntity<>(createdCategories, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        category.setId(id);
        Category updatedCategory = categoryService.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 