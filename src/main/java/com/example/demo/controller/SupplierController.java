package com.example.demo.controller;

import com.example.demo.dto.SupplierDTO;
import com.example.demo.entities.PhoneNumbersSupplier;
import com.example.demo.entities.Product;
import com.example.demo.entities.Supplier;
import com.example.demo.entities.SupplyOrder;
import com.example.demo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(
            @RequestParam String supplierName,
            @RequestParam String email) {
        
        Supplier createdSupplier = supplierService.createSupplier(supplierName, email);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer id) {
        Optional<Supplier> supplier = supplierService.findSupplierById(id);
        return supplier.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Supplier>> getSuppliersByNameKeyword(@RequestParam String keyword) {
        List<Supplier> suppliers = supplierService.getSuppliersByNameKeyword(keyword);
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<Supplier>> getSuppliersByEmailKeyword(@RequestParam String keyword) {
        List<Supplier> suppliers = supplierService.getSuppliersByEmailKeyword(keyword);
        return ResponseEntity.ok(suppliers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        Supplier updatedSupplier = supplierService.updateSupplier(supplier);
        return ResponseEntity.ok(updatedSupplier);
    }

    @PatchMapping("/{id}/info")
    public ResponseEntity<Supplier> updateSupplierInfo(
            @PathVariable Integer id,
            @RequestParam String supplierName,
            @RequestParam String email) {
        Supplier updatedSupplier = supplierService.updateSupplierInfo(id, supplierName, email);
        return ResponseEntity.ok(updatedSupplier);
    }

    @GetMapping("/{id}/phone-numbers")
    public ResponseEntity<Set<PhoneNumbersSupplier>> getSupplierPhoneNumbers(@PathVariable Integer id) {
        Set<PhoneNumbersSupplier> phoneNumbers = supplierService.getSupplierPhoneNumbers(id);
        return ResponseEntity.ok(phoneNumbers);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Set<Product>> getSupplierProducts(@PathVariable Integer id) {
        Set<Product> products = supplierService.getSupplierProducts(id);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<Set<SupplyOrder>> getSupplierOrders(@PathVariable Integer id) {
        Set<SupplyOrder> orders = supplierService.getSupplierOrders(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/with-products")
    public ResponseEntity<List<Supplier>> getSuppliersWithProducts() {
        List<Supplier> suppliers = supplierService.getSuppliersWithProducts();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/with-active-orders")
    public ResponseEntity<List<Supplier>> getSuppliersWithActiveOrders() {
        List<Supplier> suppliers = supplierService.getSuppliersWithActiveOrders();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/without-phone-numbers")
    public ResponseEntity<List<Supplier>> getSuppliersWithoutPhoneNumbers() {
        List<Supplier> suppliers = supplierService.getSuppliersWithoutPhoneNumbers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/with-supply-orders")
    public ResponseEntity<List<Supplier>> getSuppliersWithSupplyOrders() {
        List<Supplier> suppliers = supplierService.getSuppliersWithSupplyOrders();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/count/name-pattern")
    public ResponseEntity<Long> getSupplierCountByNamePattern(@RequestParam String keyword) {
        long count = supplierService.getSupplierCountByNamePattern(keyword);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
} 