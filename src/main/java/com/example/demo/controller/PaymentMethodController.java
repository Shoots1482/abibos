package com.example.demo.controller;

import com.example.demo.entities.CustomerOrder;
import com.example.demo.entities.PaymentMethod;
import com.example.demo.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(@RequestParam String methodName) {
        PaymentMethod paymentMethod = paymentMethodService.createPaymentMethod(methodName);
        return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable Integer id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodService.findPaymentMethodById(id);
        return paymentMethod.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{methodName}")
    public ResponseEntity<PaymentMethod> getPaymentMethodByName(@PathVariable String methodName) {
        Optional<PaymentMethod> paymentMethod = paymentMethodService.findPaymentMethodByName(methodName);
        return paymentMethod.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PaymentMethod>> getPaymentMethodsByNameSearch(@RequestParam String keyword) {
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsByNameSearch(keyword);
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/with-orders")
    public ResponseEntity<List<PaymentMethod>> getPaymentMethodsWithOrders() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsWithOrders();
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/without-orders")
    public ResponseEntity<List<PaymentMethod>> getPaymentMethodsWithoutOrders() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsWithoutOrders();
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/most-used")
    public ResponseEntity<Map<PaymentMethod, Long>> getMostUsedPaymentMethods(
            @RequestParam(defaultValue = "10") int limit) {
        Map<PaymentMethod, Long> mostUsedMethods = paymentMethodService.getMostUsedPaymentMethods(limit);
        return ResponseEntity.ok(mostUsedMethods);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(
            @PathVariable Integer id, 
            @RequestBody PaymentMethod paymentMethod) {
        paymentMethod.setId(id);
        PaymentMethod updatedMethod = paymentMethodService.updatePaymentMethod(paymentMethod);
        return ResponseEntity.ok(updatedMethod);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<PaymentMethod> updatePaymentMethodName(
            @PathVariable Integer id,
            @RequestParam String methodName) {
        PaymentMethod updatedMethod = paymentMethodService.updatePaymentMethodName(id, methodName);
        return ResponseEntity.ok(updatedMethod);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<Set<CustomerOrder>> getOrdersByPaymentMethod(@PathVariable Integer id) {
        Set<CustomerOrder> orders = paymentMethodService.getOrdersByPaymentMethod(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/orders/count")
    public ResponseEntity<Long> countOrdersByPaymentMethod(@PathVariable Integer id) {
        long count = paymentMethodService.countOrdersByPaymentMethod(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/name-exists")
    public ResponseEntity<Boolean> isPaymentMethodNameTaken(@RequestParam String methodName) {
        boolean exists = paymentMethodService.isPaymentMethodNameTaken(methodName);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Integer id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
} 