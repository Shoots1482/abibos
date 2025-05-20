package com.example.demo.controller;

import com.example.demo.entities.Notification;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Notification>> getNotificationsByCustomer(@PathVariable Integer customerId) {
        List<Notification> notifications = notificationService.getNotificationsByCustomer(customerId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Notification>> getNotificationsByStore(@PathVariable Integer storeId) {
        List<Notification> notifications = notificationService.getNotificationsByStore(storeId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Notification>> getNotificationsByProduct(@PathVariable Integer productId) {
        List<Notification> notifications = notificationService.getNotificationsByProduct(productId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Notification>> getNotificationsByEmployee(@PathVariable Integer employeeId) {
        List<Notification> notifications = notificationService.getNotificationsByEmployee(employeeId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Notification>> getNotificationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Notification> notifications = notificationService.getNotificationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}/mark-read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Integer id) {
        Notification notification = notificationService.markAsRead(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Notification>> searchNotifications(@RequestParam String searchTerm) {
        List<Notification> notifications = notificationService.searchNotifications(searchTerm);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread-count/customer/{customerId}")
    public ResponseEntity<Long> getUnreadCountForCustomer(@PathVariable Integer customerId) {
        Long count = notificationService.getUnreadCountForCustomer(customerId);
        return ResponseEntity.ok(count);
    }
} 