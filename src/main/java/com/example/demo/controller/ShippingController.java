package com.example.demo.controller;

import com.example.demo.dto.ShippingDTO;
import com.example.demo.entities.CustomerOrder;
import com.example.demo.entities.Shipping;
import com.example.demo.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shippings")
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping
    public ResponseEntity<Shipping> createShipping(
            @RequestBody CustomerOrder order,
            @RequestParam String trackingNumber,
            @RequestParam String shippingProvider) {
        
        Shipping shipping = shippingService.createShipping(order, trackingNumber, shippingProvider);
        return new ResponseEntity<>(shipping, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getShippingById(@PathVariable Integer id) {
        Optional<Shipping> shipping = shippingService.findShippingById(id);
        return shipping.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipping> getShippingByTrackingNumber(@PathVariable String trackingNumber) {
        Optional<Shipping> shipping = shippingService.findShippingByTrackingNumber(trackingNumber);
        return shipping.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/by-order")
    public ResponseEntity<List<Shipping>> getShippingByOrder(@RequestBody CustomerOrder order) {
        List<Shipping> shippings = shippingService.getShippingByOrder(order);
        return ResponseEntity.ok(shippings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipping> updateShipping(
            @PathVariable Integer id,
            @RequestBody Shipping shipping) {
        
        shipping.setId(id);
        Shipping updatedShipping = shippingService.updateShipping(shipping);
        return ResponseEntity.ok(updatedShipping);
    }

    @PatchMapping("/{id}/mark-shipped")
    public ResponseEntity<Shipping> markAsShipped(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant shippedDate) {
        
        Shipping shipping = shippingService.markAsShipped(id, shippedDate);
        return ResponseEntity.ok(shipping);
    }

    @PatchMapping("/{id}/mark-delivered")
    public ResponseEntity<Shipping> markAsDelivered(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant deliveredDate) {
        
        Shipping shipping = shippingService.markAsDelivered(id, deliveredDate);
        return ResponseEntity.ok(shipping);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Shipping>> getPendingShipments() {
        List<Shipping> pendingShipments = shippingService.getPendingShipments();
        return ResponseEntity.ok(pendingShipments);
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<Shipping>> getDeliveredShipments() {
        List<Shipping> deliveredShipments = shippingService.getDeliveredShipments();
        return ResponseEntity.ok(deliveredShipments);
    }

    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<Shipping>> getShipmentsByProvider(@PathVariable String provider) {
        List<Shipping> shippings = shippingService.getShipmentsByProvider(provider);
        return ResponseEntity.ok(shippings);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Shipping>> getShipmentsInDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate) {
        
        List<Shipping> shippings = shippingService.getShipmentsInDateRange(startDate, endDate);
        return ResponseEntity.ok(shippings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipping(@PathVariable Integer id) {
        shippingService.deleteShipping(id);
        return ResponseEntity.noContent().build();
    }
} 