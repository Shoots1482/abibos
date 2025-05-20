package com.example.demo.controller;

import com.example.demo.dto.PromotionDTO;
import com.example.demo.entities.Promotion;
import com.example.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        Promotion createdPromotion = promotionService.createPromotion(promotion);
        return new ResponseEntity<>(createdPromotion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(
            @PathVariable Integer id, 
            @RequestBody Promotion promotion) {
        Promotion updatedPromotion = promotionService.updatePromotion(id, promotion);
        return ResponseEntity.ok(updatedPromotion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Integer id) {
        Optional<Promotion> promotion = promotionService.getPromotionById(id);
        return promotion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Promotion> getPromotionByCode(@PathVariable String code) {
        Optional<Promotion> promotion = promotionService.getPromotionByCode(code);
        return promotion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Promotion>> getActivePromotions() {
        List<Promotion> promotions = promotionService.getActivePromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/current")
    public ResponseEntity<List<Promotion>> getCurrentPromotions() {
        List<Promotion> promotions = promotionService.getCurrentPromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/expiring-soon")
    public ResponseEntity<List<Promotion>> getPromotionsExpiringSoon(@RequestParam(defaultValue = "7") int days) {
        List<Promotion> promotions = promotionService.getPromotionsExpiringSoon(days);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/valid/{code}")
    public ResponseEntity<Boolean> isPromotionValid(@PathVariable String code) {
        boolean isValid = promotionService.isPromotionValid(code);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/{id}/applicable")
    public ResponseEntity<Boolean> isPromotionApplicable(
            @PathVariable Integer id,
            @RequestParam BigDecimal orderTotal) {
        boolean isApplicable = promotionService.isPromotionApplicable(id, orderTotal);
        return ResponseEntity.ok(isApplicable);
    }

    @GetMapping("/{id}/usage-count")
    public ResponseEntity<Long> getPromotionUsageCount(@PathVariable Integer id) {
        long usageCount = promotionService.getPromotionUsageCount(id);
        return ResponseEntity.ok(usageCount);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activatePromotion(@PathVariable Integer id) {
        promotionService.activatePromotion(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePromotion(@PathVariable Integer id) {
        promotionService.deactivatePromotion(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/discount-range")
    public ResponseEntity<List<Promotion>> searchPromotionsByDiscountRange(
            @RequestParam BigDecimal minValue,
            @RequestParam BigDecimal maxValue,
            @RequestParam(defaultValue = "true") boolean isPercentage) {
        List<Promotion> promotions = promotionService.searchPromotionsByDiscountRange(minValue, maxValue, isPercentage);
        return ResponseEntity.ok(promotions);
    }
} 