package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entities.*;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestParam String firstName,
            @RequestParam String middleName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String gender,
            @RequestParam String role,
            @RequestBody Store store,
            @RequestBody(required = false) Storage storage,
            @RequestBody(required = false) Employee supervisor) {
        
        Employee createdEmployee = employeeService.createEmployee(
                firstName, middleName, lastName, email, gender, role, store, storage, supervisor);
        
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Optional<Employee> employee = employeeService.findEmployeeByEmail(email);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@RequestParam String nameKeyword) {
        List<Employee> employees = employeeService.getEmployeesByNameSearch(nameKeyword);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Employee>> getEmployeesByGender(@PathVariable String gender) {
        List<Employee> employees = employeeService.getEmployeesByGender(gender);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Employee>> getEmployeesByRole(@PathVariable String role) {
        List<Employee> employees = employeeService.getEmployeesByRole(role);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/roles")
    public ResponseEntity<List<Employee>> getEmployeesByRoles(@RequestBody List<String> roles) {
        List<Employee> employees = employeeService.getEmployeesByRoles(roles);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/store")
    public ResponseEntity<List<Employee>> getEmployeesByStore(@RequestBody Store store) {
        List<Employee> employees = employeeService.getEmployeesByStore(store);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/storage")
    public ResponseEntity<List<Employee>> getEmployeesByStorage(@RequestBody Storage storage) {
        List<Employee> employees = employeeService.getEmployeesByStorage(storage);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/supervisor")
    public ResponseEntity<List<Employee>> getEmployeesBySupervisor(@RequestBody Employee supervisor) {
        List<Employee> employees = employeeService.getEmployeesBySupervisor(supervisor);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/without-supervisor")
    public ResponseEntity<List<Employee>> getEmployeesWithoutSupervisor() {
        List<Employee> employees = employeeService.getEmployeesWithoutSupervisor();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/store-managers")
    public ResponseEntity<List<Employee>> getStoreManagers(@RequestBody Store store) {
        List<Employee> managers = employeeService.getStoreManagers(store);
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/with-direct-reports")
    public ResponseEntity<List<Employee>> getEmployeesWithDirectReports() {
        List<Employee> employees = employeeService.getEmployeesWithDirectReports();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/with-sales")
    public ResponseEntity<List<Employee>> getEmployeesWithSales() {
        List<Employee> employees = employeeService.getEmployeesWithSales();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/with-supply-orders")
    public ResponseEntity<List<Employee>> getEmployeesWithSupplyOrders() {
        List<Employee> employees = employeeService.getEmployeesWithSupplyOrders();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/with-audit-logs")
    public ResponseEntity<List<Employee>> getEmployeesWithAuditLogs() {
        List<Employee> employees = employeeService.getEmployeesWithAuditLogs();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/store-role")
    public ResponseEntity<List<Employee>> getEmployeesByStoreAndRole(
            @RequestBody Store store,
            @RequestParam String role) {
        List<Employee> employees = employeeService.getEmployeesByStoreAndRole(store, role);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/top-selling")
    public ResponseEntity<Map<Employee, Long>> getTopSellingEmployees(
            @RequestParam(defaultValue = "10") int limit) {
        Map<Employee, Long> topEmployees = employeeService.getTopSellingEmployees(limit);
        return ResponseEntity.ok(topEmployees);
    }

    @GetMapping("/supervisors")
    public ResponseEntity<List<Employee>> getAllSupervisors() {
        List<Employee> supervisors = employeeService.getAllSupervisors();
        return ResponseEntity.ok(supervisors);
    }

    @GetMapping("/{id}/hierarchy")
    public ResponseEntity<List<Employee>> getEmployeeHierarchy(@PathVariable Integer id) {
        List<Employee> hierarchy = employeeService.getEmployeeHierarchy(id);
        return ResponseEntity.ok(hierarchy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setId(id);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{id}/contact")
    public ResponseEntity<Employee> updateEmployeeContactInfo(
            @PathVariable Integer id,
            @RequestParam String email) {
        Employee updatedEmployee = employeeService.updateEmployeeContactInfo(id, email);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<Employee> updateEmployeeRole(
            @PathVariable Integer id,
            @RequestParam String role) {
        Employee updatedEmployee = employeeService.updateEmployeeRole(id, role);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{id}/supervisor")
    public ResponseEntity<Employee> updateEmployeeSupervisor(
            @PathVariable Integer id,
            @RequestBody Employee supervisor) {
        Employee updatedEmployee = employeeService.updateEmployeeSupervisor(id, supervisor);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{id}/store")
    public ResponseEntity<Employee> updateEmployeeStore(
            @PathVariable Integer id,
            @RequestBody Store store) {
        Employee updatedEmployee = employeeService.updateEmployeeStore(id, store);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{id}/direct-reports")
    public ResponseEntity<Set<Employee>> getEmployeeDirectReports(@PathVariable Integer id) {
        Set<Employee> directReports = employeeService.getEmployeeDirectReports(id);
        return ResponseEntity.ok(directReports);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<Set<CustomerOrder>> getEmployeeCustomerOrders(@PathVariable Integer id) {
        Set<CustomerOrder> orders = employeeService.getEmployeeCustomerOrders(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/phone-numbers")
    public ResponseEntity<Set<PhoneNumbersEmployee>> getEmployeePhoneNumbers(@PathVariable Integer id) {
        Set<PhoneNumbersEmployee> phoneNumbers = employeeService.getEmployeePhoneNumbers(id);
        return ResponseEntity.ok(phoneNumbers);
    }

    @GetMapping("/{id}/supply-orders")
    public ResponseEntity<Set<SupplyOrder>> getEmployeeSupplyOrders(@PathVariable Integer id) {
        Set<SupplyOrder> supplyOrders = employeeService.getEmployeeSupplyOrders(id);
        return ResponseEntity.ok(supplyOrders);
    }

    @GetMapping("/{id}/audit-logs")
    public ResponseEntity<Set<AuditLog>> getEmployeeAuditLogs(@PathVariable Integer id) {
        Set<AuditLog> auditLogs = employeeService.getEmployeeAuditLogs(id);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> countEmployeesByRole(@PathVariable String role) {
        long count = employeeService.countEmployeesByRole(role);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/count/store")
    public ResponseEntity<Long> countEmployeesByStore(@RequestBody Store store) {
        long count = employeeService.countEmployeesByStore(store);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/email-taken")
    public ResponseEntity<Boolean> isEmailTaken(@RequestParam String email) {
        boolean isTaken = employeeService.isEmailTaken(email);
        return ResponseEntity.ok(isTaken);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
} 