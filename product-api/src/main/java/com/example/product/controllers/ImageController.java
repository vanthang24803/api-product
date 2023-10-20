package com.example.product.controllers;

import com.example.product.dto.ImageDto;
import com.example.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/product/{productId}/image")
    public ResponseEntity<?> createImage(
            @PathVariable("productId") Long productId,
            @RequestBody ImageDto imageDto
    ) {
        try {
            ImageDto savedImage = imageService.createImage(productId, imageDto);
            return ResponseEntity.ok(savedImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/{productId}/images")
    public ResponseEntity<?> getAllImages(
            @PathVariable("productId") Long productId
    ) {
        try {
            List<ImageDto> listImages = imageService.getAllImagesByProductId(productId);
            return ResponseEntity.ok(listImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/product/{productId}/image/{imageId}")
    public ResponseEntity<?> updateImage(
            @PathVariable("productId") Long productId,
            @PathVariable("imageId") Long imageId,
            @RequestBody ImageDto imageDto
    ) {
        try {
            ImageDto respone = imageService.updateImage(productId, imageId, imageDto);
            return ResponseEntity.ok(respone);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{productId}/image/{imageId}")
    public ResponseEntity<String> deleteImage(
            @PathVariable("productId") Long productId,
            @PathVariable("imageId") Long imageId
    ) {
        try {
            imageService.deleteImage(productId, imageId);
            return ResponseEntity.ok("Image delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
