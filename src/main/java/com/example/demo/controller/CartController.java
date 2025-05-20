package com.example.demo.controller;

import com.example.demo.entities.Cart;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Product;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) {
        Optional<Cart> cart = cartService.getCartById(id);
        return cart.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/customer")
    public ResponseEntity<List<Cart>> getCartsByCustomer(@RequestBody Customer customer) {
        List<Cart> carts = cartService.getCartsByCustomer(customer);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/product")
    public ResponseEntity<List<Cart>> getCartsByProduct(@RequestBody Product product) {
        List<Cart> carts = cartService.getCartsByProduct(product);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/customer-product")
    public ResponseEntity<Cart> getCartByCustomerAndProduct(
            @RequestBody Customer customer,
            @RequestBody Product product) {
        Optional<Cart> cart = cartService.getCartByCustomerAndProduct(customer, product);
        return cart.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart savedCart = cartService.saveCart(cart);
        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Integer id, @RequestBody Cart cart) {
        if (!cartService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cart.setId(id);
        Cart updatedCart = cartService.saveCart(cart);
        return ResponseEntity.ok(updatedCart);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Void> updateCartQuantity(
            @PathVariable Integer id,
            @RequestParam Integer quantity) {
        if (!cartService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartService.updateCartQuantity(id, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        if (!cartService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
} 