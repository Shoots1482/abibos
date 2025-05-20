package com.example.demo.controller;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.entities.Customer;
import com.example.demo.entities.CustomerOrder;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Optional<Customer> customer = customerService.findByEmail(email);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<Customer>> getCustomersByLastName(@PathVariable String lastName) {
        List<Customer> customers = customerService.findByLastName(lastName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<List<Customer>> getCustomersByFirstName(@PathVariable String firstName) {
        List<Customer> customers = customerService.findByFirstName(firstName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/birthDateBefore")
    public ResponseEntity<List<Customer>> getCustomersByBirthDateBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Customer> customers = customerService.findByBirthDateBefore(date);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/birthDateRange")
    public ResponseEntity<List<Customer>> getCustomersByBirthDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Customer> customers = customerService.findByBirthDateRange(startDate, endDate);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Customer>> getCustomersByGender(@PathVariable String gender) {
        List<Customer> customers = customerService.findByGender(gender);
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.save(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        if (!customerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        Customer updatedCustomer = customerService.update(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        if (!customerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/id/{id}")
    public ResponseEntity<Boolean> checkCustomerExistsById(@PathVariable Integer id) {
        boolean exists = customerService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkCustomerExistsByEmail(@PathVariable String email) {
        boolean exists = customerService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Customer> restoreCustomer(@PathVariable Integer id) {
        Customer restoredCustomer = customerService.restoreCustomer(id);
        return ResponseEntity.ok(restoredCustomer);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Customer>> getInactiveCustomers() {
        List<Customer> inactiveCustomers = customerService.findInactiveCustomers();
        return ResponseEntity.ok(inactiveCustomers);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCustomerCount() {
        long count = customerService.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<CustomerOrder>> getCustomerOrders(@PathVariable Integer id) {
        if (!customerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<CustomerOrder> orders = customerService.getCustomerOrders(id);
        return ResponseEntity.ok(orders);
    }
} 