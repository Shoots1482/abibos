package com.example.demo.controller;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.entities.Review;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewService.saveReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        review.setId(id);
        Review updatedReview = reviewService.saveReview(review);
        return ResponseEntity.ok(updatedReview);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Integer id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Integer productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@PathVariable Integer customerId) {
        List<Review> reviews = reviewService.getReviewsByCustomer(customerId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/customer/{customerId}/product/{productId}")
    public ResponseEntity<Review> getCustomerReviewForProduct(
            @PathVariable Integer customerId, 
            @PathVariable Integer productId) {
        Optional<Review> review = reviewService.getCustomerReviewForProduct(customerId, productId);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Review>> getReviewsByRatingRange(
            @RequestParam Integer minRating, 
            @RequestParam Integer maxRating) {
        List<Review> reviews = reviewService.getReviewsByRatingRange(minRating, maxRating);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/product/{productId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForProduct(@PathVariable Integer productId) {
        Optional<Double> averageRating = reviewService.getAverageRatingForProduct(productId);
        return averageRating.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Long> getReviewCountForProduct(@PathVariable Integer productId) {
        long count = reviewService.getReviewCountForProduct(productId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Review>> getRecentReviews(@RequestParam(defaultValue = "10") int limit) {
        List<Review> reviews = reviewService.getRecentReviews(limit);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Review>> searchReviews(@RequestParam String searchTerm) {
        List<Review> reviews = reviewService.searchReviews(searchTerm);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
} 