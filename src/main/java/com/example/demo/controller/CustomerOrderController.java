package com.example.demo.controller;

import com.example.demo.dto.CustomerOrderDTO;
import com.example.demo.entities.*;
import com.example.demo.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping
    public ResponseEntity<CustomerOrder> createOrder(
            @RequestBody Customer customer,
            @RequestParam(required = false) Integer employeeSellerId,
            @RequestBody PaymentMethod paymentMethod,
            @RequestBody Address address,
            @RequestBody(required = false) Promotion promotion,
            @RequestParam BigDecimal totalPrice,
            @RequestParam String status) {

        CustomerOrder createdOrder = customerOrderService.createOrder(
                customer, null, paymentMethod, address, promotion, totalPrice, status);
        
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Integer id) {
        Optional<CustomerOrder> order = customerOrderService.findOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {
        List<CustomerOrder> orders = customerOrderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/customer")
    public ResponseEntity<List<CustomerOrder>> getOrdersByCustomer(@RequestBody Customer customer) {
        List<CustomerOrder> orders = customerOrderService.getOrdersByCustomer(customer);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/employee")
    public ResponseEntity<List<CustomerOrder>> getOrdersByEmployee(@RequestBody Employee employee) {
        List<CustomerOrder> orders = customerOrderService.getOrdersByEmployee(employee);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/payment-method")
    public ResponseEntity<List<CustomerOrder>> getOrdersByPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        List<CustomerOrder> orders = customerOrderService.getOrdersByPaymentMethod(paymentMethod);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/promotion")
    public ResponseEntity<List<CustomerOrder>> getOrdersByPromotion(@RequestBody Promotion promotion) {
        List<CustomerOrder> orders = customerOrderService.getOrdersByPromotion(promotion);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CustomerOrder>> getOrdersByStatus(@PathVariable String status) {
        List<CustomerOrder> orders = customerOrderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date")
    public ResponseEntity<List<CustomerOrder>> getOrdersByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<CustomerOrder> orders = customerOrderService.getOrdersByDate(date);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<CustomerOrder>> getOrdersBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<CustomerOrder> orders = customerOrderService.getOrdersBetweenDates(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/min-price")
    public ResponseEntity<List<CustomerOrder>> getOrdersWithMinimumTotalPrice(@RequestParam BigDecimal minPrice) {
        List<CustomerOrder> orders = customerOrderService.getOrdersWithMinimumTotalPrice(minPrice);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/max-price")
    public ResponseEntity<List<CustomerOrder>> getOrdersWithMaximumTotalPrice(@RequestParam BigDecimal maxPrice) {
        List<CustomerOrder> orders = customerOrderService.getOrdersWithMaximumTotalPrice(maxPrice);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/customer/status")
    public ResponseEntity<List<CustomerOrder>> getOrdersByCustomerAndStatus(
            @RequestBody Customer customer,
            @RequestParam String status) {
        List<CustomerOrder> orders = customerOrderService.getOrdersByCustomerAndStatus(customer, status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<CustomerOrder>> getRecentOrders(@RequestParam(defaultValue = "7") int daysBack) {
        List<CustomerOrder> orders = customerOrderService.getRecentOrders(daysBack);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/with-returns")
    public ResponseEntity<List<CustomerOrder>> getOrdersWithReturns() {
        List<CustomerOrder> orders = customerOrderService.getOrdersWithReturns();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> calculateTotalRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal revenue = customerOrderService.calculateTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    @PostMapping("/revenue/customer")
    public ResponseEntity<BigDecimal> calculateTotalRevenueByCustomer(@RequestBody Customer customer) {
        BigDecimal revenue = customerOrderService.calculateTotalRevenueByCustomer(customer);
        return ResponseEntity.ok(revenue);
    }

    @PostMapping("/revenue/employee")
    public ResponseEntity<BigDecimal> calculateTotalRevenueByEmployee(@RequestBody Employee employee) {
        BigDecimal revenue = customerOrderService.calculateTotalRevenueByEmployee(employee);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getOrderCountByStatus(@PathVariable String status) {
        long count = customerOrderService.getOrderCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/top-customers")
    public ResponseEntity<Map<Customer, Long>> getTopCustomersByOrderCount(@RequestParam(defaultValue = "10") int limit) {
        Map<Customer, Long> topCustomers = customerOrderService.getTopCustomersByOrderCount(limit);
        return ResponseEntity.ok(topCustomers);
    }

    @GetMapping("/top-employees")
    public ResponseEntity<Map<Employee, BigDecimal>> getTopEmployeesBySales(@RequestParam(defaultValue = "10") int limit) {
        Map<Employee, BigDecimal> topEmployees = customerOrderService.getTopEmployeesBySales(limit);
        return ResponseEntity.ok(topEmployees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrder> updateOrder(@PathVariable Integer id, @RequestBody CustomerOrder order) {
        order.setId(id);
        CustomerOrder updatedOrder = customerOrderService.updateOrder(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CustomerOrder> updateOrderStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        CustomerOrder updatedOrder = customerOrderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Set<OrderDetail>> getOrderDetails(@PathVariable Integer id) {
        Set<OrderDetail> orderDetails = customerOrderService.getOrderDetails(id);
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/{id}/returns")
    public ResponseEntity<Set<Return>> getOrderReturns(@PathVariable Integer id) {
        Set<Return> returns = customerOrderService.getOrderReturns(id);
        return ResponseEntity.ok(returns);
    }

    @GetMapping("/{id}/shippings")
    public ResponseEntity<Set<Shipping>> getOrderShippings(@PathVariable Integer id) {
        Set<Shipping> shippings = customerOrderService.getOrderShippings(id);
        return ResponseEntity.ok(shippings);
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> calculateOrderTotal(@PathVariable Integer id) {
        BigDecimal total = customerOrderService.calculateOrderTotal(id);
        return ResponseEntity.ok(total);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        customerOrderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
} 