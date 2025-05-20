package com.example.demo.controller;

import com.example.demo.dto.StoreDTO;
import com.example.demo.entities.*;
import com.example.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<Store> createStore(
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam Integer minStock) {
        
        Store createdStore = storeService.createStore(address, email, minStock);
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Integer id) {
        Optional<Store> store = storeService.findStoreById(id);
        return store.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<Store>> getStoresByAddressKeyword(@RequestParam String keyword) {
        List<Store> stores = storeService.getStoresByAddressKeyword(keyword);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<Store>> getStoresByEmailKeyword(@RequestParam String keyword) {
        List<Store> stores = storeService.getStoresByEmailKeyword(keyword);
        return ResponseEntity.ok(stores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Integer id, @RequestBody Store store) {
        store.setId(id);
        Store updatedStore = storeService.updateStore(store);
        return ResponseEntity.ok(updatedStore);
    }

    @PatchMapping("/{id}/contact")
    public ResponseEntity<Store> updateStoreContactInfo(
            @PathVariable Integer id,
            @RequestParam String address,
            @RequestParam String email) {
        Store updatedStore = storeService.updateStoreContactInfo(id, address, email);
        return ResponseEntity.ok(updatedStore);
    }

    @PatchMapping("/{id}/min-stock")
    public ResponseEntity<Store> updateStoreMinStock(
            @PathVariable Integer id,
            @RequestParam Integer minStock) {
        Store updatedStore = storeService.updateStoreMinStock(id, minStock);
        return ResponseEntity.ok(updatedStore);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Set<Employee>> getStoreEmployees(@PathVariable Integer id) {
        Set<Employee> employees = storeService.getStoreEmployees(id);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/storage-locations")
    public ResponseEntity<Set<Storage>> getStoreStorageLocations(@PathVariable Integer id) {
        Set<Storage> storageLocations = storeService.getStoreStorageLocations(id);
        return ResponseEntity.ok(storageLocations);
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<Set<StoreInventory>> getStoreInventory(@PathVariable Integer id) {
        Set<StoreInventory> inventory = storeService.getStoreInventory(id);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/{id}/notifications")
    public ResponseEntity<Set<Notification>> getStoreNotifications(@PathVariable Integer id) {
        Set<Notification> notifications = storeService.getStoreNotifications(id);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}/reserved-stock")
    public ResponseEntity<Set<ReservedStock>> getStoreReservedStock(@PathVariable Integer id) {
        Set<ReservedStock> reservedStock = storeService.getStoreReservedStock(id);
        return ResponseEntity.ok(reservedStock);
    }

    @GetMapping("/low-inventory")
    public ResponseEntity<List<Store>> getStoresWithLowInventory() {
        List<Store> stores = storeService.getStoresWithLowInventory();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/with-storage")
    public ResponseEntity<List<Store>> getStoresWithStorageLocations() {
        List<Store> stores = storeService.getStoresWithStorageLocations();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/no-employees")
    public ResponseEntity<List<Store>> getStoresWithNoEmployees() {
        List<Store> stores = storeService.getStoresWithNoEmployees();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/reserved-stock")
    public ResponseEntity<List<Store>> getStoresWithReservedStock() {
        List<Store> stores = storeService.getStoresWithReservedStock();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/unread-notifications")
    public ResponseEntity<List<Store>> getStoresWithUnreadNotifications() {
        List<Store> stores = storeService.getStoresWithUnreadNotifications();
        return ResponseEntity.ok(stores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Integer id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
} 