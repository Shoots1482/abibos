package com.example.demo.controller;

import com.example.demo.entities.Image;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Image> addImage(
            @RequestParam Integer productId,
            @RequestParam String imageUrl,
            @RequestParam(defaultValue = "false") boolean isPrimary) {
        
        Image addedImage = imageService.addImage(productId, imageUrl, isPrimary);
        return new ResponseEntity<>(addedImage, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Image>> getImagesByProduct(@PathVariable Integer productId) {
        List<Image> images = imageService.getImagesByProduct(productId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/primary/product/{productId}")
    public ResponseEntity<Image> getPrimaryImage(@PathVariable Integer productId) {
        Image primaryImage = imageService.getPrimaryImage(productId);
        if (primaryImage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(primaryImage);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteAllImagesForProduct(@PathVariable Integer productId) {
        imageService.deleteAllImagesForProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{imageId}/set-primary")
    public ResponseEntity<Image> setAsPrimary(@PathVariable Integer imageId) {
        Image updatedImage = imageService.setAsPrimary(imageId);
        return ResponseEntity.ok(updatedImage);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> imageUrlExists(@RequestParam String imageUrl) {
        boolean exists = imageService.imageUrlExists(imageUrl);
        return ResponseEntity.ok(exists);
    }
} 