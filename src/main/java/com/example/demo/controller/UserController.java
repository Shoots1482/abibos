package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entities.AuditLog;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Session;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody Customer customer,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role) {
        
        User createdUser = userService.createUser(customer, email, password, role);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/customer")
    public ResponseEntity<List<User>> getUsersByCustomer(@RequestBody Customer customer) {
        List<User> users = userService.getUsersByCustomer(customer);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<User> updateUserEmail(
            @PathVariable Integer id,
            @RequestParam String newEmail) {
        User updatedUser = userService.updateUserEmail(id, newEmail);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<User> updateUserPassword(
            @PathVariable Integer id,
            @RequestParam String newPassword) {
        User updatedUser = userService.updateUserPassword(id, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateUserRole(
            @PathVariable Integer id,
            @RequestParam String newRole) {
        User updatedUser = userService.updateUserRole(id, newRole);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<Set<Session>> getUserSessions(@PathVariable Integer id) {
        Set<Session> sessions = userService.getUserSessions(id);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}/audit-logs")
    public ResponseEntity<Set<AuditLog>> getUserAuditLogs(@PathVariable Integer id) {
        Set<AuditLog> auditLogs = userService.getUserAuditLogs(id);
        return ResponseEntity.ok(auditLogs);
    }

    @PatchMapping("/{id}/lock")
    public ResponseEntity<User> lockUserAccount(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant lockedUntil) {
        User lockedUser = userService.lockUserAccount(id, lockedUntil);
        return ResponseEntity.ok(lockedUser);
    }

    @PatchMapping("/{id}/unlock")
    public ResponseEntity<User> unlockUserAccount(@PathVariable Integer id) {
        User unlockedUser = userService.unlockUserAccount(id);
        return ResponseEntity.ok(unlockedUser);
    }

    @PostMapping("/{id}/failed-login")
    public ResponseEntity<User> recordFailedLoginAttempt(@PathVariable Integer id) {
        User user = userService.recordFailedLoginAttempt(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/reset-login-attempts")
    public ResponseEntity<User> resetFailedLoginAttempts(@PathVariable Integer id) {
        User user = userService.resetFailedLoginAttempts(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/is-locked")
    public ResponseEntity<Boolean> isUserLocked(@PathVariable Integer id) {
        boolean isLocked = userService.isUserLocked(id);
        return ResponseEntity.ok(isLocked);
    }

    @GetMapping("/with-active-sessions")
    public ResponseEntity<List<User>> getUsersWithActiveSessions() {
        List<User> users = userService.getUsersWithActiveSessions();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/locked")
    public ResponseEntity<List<User>> getLockedUsers() {
        List<User> users = userService.getLockedUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/failed-login-attempts")
    public ResponseEntity<List<User>> getUsersWithFailedLoginAttempts(@RequestParam Integer threshold) {
        List<User> users = userService.getUsersWithFailedLoginAttempts(threshold);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<User>> getRecentlyCreatedUsers(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant since) {
        List<User> users = userService.getRecentlyCreatedUsers(since);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<User>> searchUsersByEmailKeyword(@RequestParam String keyword) {
        List<User> users = userService.searchUsersByEmailKeyword(keyword);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> countUsersByRole(@PathVariable String role) {
        long count = userService.countUsersByRole(role);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/email-taken")
    public ResponseEntity<Boolean> isEmailTaken(@RequestParam String email) {
        boolean isTaken = userService.isEmailTaken(email);
        return ResponseEntity.ok(isTaken);
    }

    @GetMapping("/with-audit-logs")
    public ResponseEntity<List<User>> getUsersWithAuditLogs() {
        List<User> users = userService.getUsersWithAuditLogs();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
} 