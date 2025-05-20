package com.example.demo.controller;

import com.example.demo.dto.WishlistDTO;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Product;
import com.example.demo.entities.Wishlist;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<Wishlist> addToWishlist(
            @RequestBody Customer customer,
            @RequestBody Product product) {
        Wishlist wishlist = wishlistService.addToWishlist(customer, product);
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Integer id) {
        Optional<Wishlist> wishlist = wishlistService.findWishlistById(id);
        return wishlist.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getAllWishlists() {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        return ResponseEntity.ok(wishlists);
    }

    @PostMapping("/customer")
    public ResponseEntity<List<Wishlist>> getCustomerWishlist(@RequestBody Customer customer) {
        List<Wishlist> wishlists = wishlistService.getCustomerWishlist(customer);
        return ResponseEntity.ok(wishlists);
    }

    @PostMapping("/product")
    public ResponseEntity<List<Wishlist>> getWishlistsByProduct(@RequestBody Product product) {
        List<Wishlist> wishlists = wishlistService.getWishlistsByProduct(product);
        return ResponseEntity.ok(wishlists);
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> isProductInWishlist(
            @RequestBody Customer customer,
            @RequestBody Product product) {
        boolean inWishlist = wishlistService.isProductInWishlist(customer, product);
        return ResponseEntity.ok(inWishlist);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Wishlist>> getWishlistsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Wishlist> wishlists = wishlistService.getWishlistsByDate(date);
        return ResponseEntity.ok(wishlists);
    }

    @GetMapping("/after")
    public ResponseEntity<List<Wishlist>> getWishlistsAddedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Wishlist> wishlists = wishlistService.getWishlistsAddedAfter(date);
        return ResponseEntity.ok(wishlists);
    }

    @GetMapping("/before")
    public ResponseEntity<List<Wishlist>> getWishlistsAddedBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Wishlist> wishlists = wishlistService.getWishlistsAddedBefore(date);
        return ResponseEntity.ok(wishlists);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Wishlist>> getWishlistsAddedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Wishlist> wishlists = wishlistService.getWishlistsAddedBetween(startDate, endDate);
        return ResponseEntity.ok(wishlists);
    }

    @PostMapping("/count/customer")
    public ResponseEntity<Long> countCustomerWishlists(@RequestBody Customer customer) {
        long count = wishlistService.countCustomerWishlists(customer);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/count/product")
    public ResponseEntity<Long> countProductWishlists(@RequestBody Product product) {
        long count = wishlistService.countProductWishlists(product);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/popular")
    public ResponseEntity<Map<Product, Long>> getMostPopularWishlistProducts(
            @RequestParam(defaultValue = "10") int limit) {
        Map<Product, Long> popularProducts = wishlistService.getMostPopularWishlistProducts(limit);
        return ResponseEntity.ok(popularProducts);
    }

    @PostMapping("/customers-with-product")
    public ResponseEntity<List<Customer>> getCustomersWhoAddedProduct(@RequestBody Product product) {
        List<Customer> customers = wishlistService.getCustomersWhoAddedProduct(product);
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFromWishlist(
            @RequestBody Customer customer,
            @RequestBody Product product) {
        wishlistService.removeFromWishlist(customer, product);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWishlist(@PathVariable Integer id) {
        wishlistService.removeWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCustomerWishlist(@RequestBody Customer customer) {
        wishlistService.clearCustomerWishlist(customer);
        return ResponseEntity.noContent().build();
    }
} 