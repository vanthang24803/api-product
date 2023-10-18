package com.example.product.controllers;

import com.example.product.dto.ReviewDto;
import com.example.product.dto.ReviewResponse;
import com.example.product.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/product/{productId}/review")
    public ResponseEntity<?> createReview(
            @PathVariable("productId") Long productId,
            @RequestBody ReviewDto reviewDto
    ) {
        try {
            ReviewDto savedReview = reviewService.createReview(productId, reviewDto);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/{productId}/reviews")
    public ResponseEntity<?> getAllReviews(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "numberPage", defaultValue = "0", required = false) int numberPage,
            @RequestParam(value = "sizePage", defaultValue = "5", required = false) int sizePage
    ) {
        try {
            ReviewResponse response = reviewService.getAllReview(numberPage ,sizePage,productId);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/product/{productId}/review/{reviewId}")
    public  ResponseEntity<?> updateReview(
            @PathVariable("productId") Long productId,
            @PathVariable("reviewId") Long reviewId,
            @RequestBody ReviewDto reviewDto
    ){
        try {
            ReviewDto respone = reviewService.updateReview(productId,reviewId,reviewDto);
            return ResponseEntity.ok(respone);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{productId}/review/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable("productId") Long productId,
            @PathVariable("reviewId") Long reviewId
    ){
        try{
            reviewService.deleteReview(productId,reviewId);
            return ResponseEntity.ok("Delete review successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
