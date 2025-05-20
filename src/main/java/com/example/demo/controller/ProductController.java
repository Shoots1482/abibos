package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.*;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestParam String productName,
            @RequestParam String size,
            @RequestParam String brand,
            @RequestParam BigDecimal price,
            @RequestParam String color,
            @RequestParam String description,
            @RequestBody Supplier supplier) {
        
        Product createdProduct = productService.createProduct(
                productName, size, brand, price, color, description, supplier);
        
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productService.findProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Product>> getProductsByNameKeyword(@RequestParam String keyword) {
        List<Product> products = productService.getProductsByNameKeyword(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/color/{color}")
    public ResponseEntity<List<Product>> getProductsByColor(@PathVariable String color) {
        List<Product> products = productService.getProductsByColor(color);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/size/{size}")
    public ResponseEntity<List<Product>> getProductsBySize(@PathVariable String size) {
        List<Product> products = productService.getProductsBySize(size);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/search/supplier")
    public ResponseEntity<List<Product>> getProductsBySupplier(@RequestBody Supplier supplier) {
        List<Product> products = productService.getProductsBySupplier(supplier);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        List<Product> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Product>> getInactiveProducts() {
        List<Product> products = productService.getInactiveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/launched-after")
    public ResponseEntity<List<Product>> getProductsLaunchedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Product> products = productService.getProductsLaunchedAfter(date);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice, 
            @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/max-price")
    public ResponseEntity<List<Product>> getProductsByMaxPrice(@RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByMaxPrice(maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/min-price")
    public ResponseEntity<List<Product>> getProductsByMinPrice(@RequestParam BigDecimal minPrice) {
        List<Product> products = productService.getProductsByMinPrice(minPrice);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/search/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestBody Category category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/search/categories")
    public ResponseEntity<List<Product>> getProductsByCategories(@RequestBody List<Category> categories) {
        List<Product> products = productService.getProductsByCategories(categories);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/brand-price")
    public ResponseEntity<List<Product>> getProductsByBrandAndPriceRange(
            @RequestParam String brand,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByBrandAndPriceRange(brand, minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/brand-color")
    public ResponseEntity<List<Product>> getProductsByBrandAndColor(
            @RequestParam String brand, 
            @RequestParam String color) {
        List<Product> products = productService.getProductsByBrandAndColor(brand, color);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Product>> getLatestProducts() {
        List<Product> products = productService.getLatestProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/with-reviews")
    public ResponseEntity<List<Product>> getProductsWithReviews() {
        List<Product> products = productService.getProductsWithReviews();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/min-rating")
    public ResponseEntity<Map<Product, Double>> getProductsWithMinimumAverageRating(
            @RequestParam Double minRating) {
        Map<Product, Double> productRatings = productService.getProductsWithMinimumAverageRating(minRating);
        return ResponseEntity.ok(productRatings);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getProductsInStock() {
        List<Product> products = productService.getProductsInStock();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top-selling")
    public ResponseEntity<Map<Product, Long>> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit) {
        Map<Product, Long> topSellers = productService.getTopSellingProducts(limit);
        return ResponseEntity.ok(topSellers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Product> updateProductPrice(
            @PathVariable Integer id,
            @RequestParam BigDecimal newPrice) {
        Product updatedProduct = productService.updateProductPrice(id, newPrice);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Product> updateProductStatus(
            @PathVariable Integer id,
            @RequestParam Boolean isActive) {
        Product updatedProduct = productService.updateProductStatus(id, isActive);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/{id}/categories")
    public ResponseEntity<Product> addProductToCategory(
            @PathVariable Integer id,
            @RequestBody Category category) {
        Product updatedProduct = productService.addProductToCategory(id, category);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}/categories")
    public ResponseEntity<Product> removeProductFromCategory(
            @PathVariable Integer id,
            @RequestBody Category category) {
        Product updatedProduct = productService.removeProductFromCategory(id, category);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<Set<Category>> getProductCategories(@PathVariable Integer id) {
        Set<Category> categories = productService.getProductCategories(id);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<Set<Image>> getProductImages(@PathVariable Integer id) {
        Set<Image> images = productService.getProductImages(id);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<Set<Review>> getProductReviews(@PathVariable Integer id) {
        Set<Review> reviews = productService.getProductReviews(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}/price-history")
    public ResponseEntity<Set<PriceHistory>> getProductPriceHistory(@PathVariable Integer id) {
        Set<PriceHistory> priceHistory = productService.getProductPriceHistory(id);
        return ResponseEntity.ok(priceHistory);
    }

    @GetMapping("/{id}/average-rating")
    public ResponseEntity<Double> calculateAverageProductRating(@PathVariable Integer id) {
        Double averageRating = productService.calculateAverageProductRating(id);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/count/brand/{brand}")
    public ResponseEntity<Long> countProductsByBrand(@PathVariable String brand) {
        long count = productService.countProductsByBrand(brand);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> productExistsByNameAndBrand(
            @RequestParam String productName, 
            @RequestParam String brand) {
        boolean exists = productService.productExistsByNameAndBrand(productName, brand);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
} 