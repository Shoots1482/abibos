package com.example.demo.controller;

import com.example.demo.entities.OrderDetail;
import com.example.demo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping
    public ResponseEntity<OrderDetail> saveOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail savedOrderDetail = orderDetailService.saveOrderDetail(orderDetail);
        return new ResponseEntity<>(savedOrderDetail, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrder(@PathVariable Integer orderId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrder(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByProduct(@PathVariable Integer productId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByProduct(productId);
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetail> getOrderDetail(
            @PathVariable Integer orderId,
            @PathVariable Integer productId) {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(orderId, productId);
        if (orderDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetail);
    }

    @PatchMapping("/{orderId}/{productId}/quantity")
    public ResponseEntity<OrderDetail> updateQuantity(
            @PathVariable Integer orderId,
            @PathVariable Integer productId,
            @RequestParam Integer quantity) {
        OrderDetail updatedOrderDetail = orderDetailService.updateQuantity(orderId, productId, quantity);
        if (updatedOrderDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOrderDetail);
    }

    @DeleteMapping("/{orderId}/{productId}")
    public ResponseEntity<Void> deleteOrderDetail(
            @PathVariable Integer orderId,
            @PathVariable Integer productId) {
        orderDetailService.deleteOrderDetail(orderId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteAllOrderDetailsForOrder(@PathVariable Integer orderId) {
        orderDetailService.deleteAllOrderDetailsForOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}/total-quantity")
    public ResponseEntity<Integer> getTotalQuantitySoldForProduct(@PathVariable Integer productId) {
        Integer totalQuantity = orderDetailService.getTotalQuantitySoldForProduct(productId);
        return ResponseEntity.ok(totalQuantity);
    }

    @GetMapping("/product/{productId}/total-revenue")
    public ResponseEntity<BigDecimal> getTotalRevenueForProduct(@PathVariable Integer productId) {
        BigDecimal totalRevenue = orderDetailService.getTotalRevenueForProduct(productId);
        return ResponseEntity.ok(totalRevenue);
    }
} 