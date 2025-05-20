package com.example.demo.controller;

import com.example.demo.dto.AddressDTO;
import com.example.demo.entities.Address;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address createdAddress = addressService.createAddress(address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        address.setId(id);
        Address updatedAddress = addressService.updateAddress(address);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Optional<Address> address = addressService.findById(id);
        return address.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.findAll();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Address>> getAddressesByCustomerId(@PathVariable Integer customerId) {
        List<Address> addresses = addressService.findAddressByCustomerId(customerId);
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{addressId}/default/customer/{customerId}")
    public ResponseEntity<Address> setDefaultAddress(
            @PathVariable Integer addressId, 
            @PathVariable Integer customerId) {
        Address defaultAddress = addressService.setDefaultAddress(addressId, customerId);
        return ResponseEntity.ok(defaultAddress);
    }

    @GetMapping("/default/customer/{customerId}")
    public ResponseEntity<Address> getDefaultAddressForCustomer(@PathVariable Integer customerId) {
        Optional<Address> defaultAddress = addressService.getDefaultAddressForCustomer(customerId);
        return defaultAddress.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateAddress(@RequestBody Address address) {
        boolean isValid = addressService.isValidAddress(address);
        return ResponseEntity.ok(isValid);
    }
}
