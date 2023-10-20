package com.example.product.service;

import com.example.product.dto.ReviewDto;
import com.example.product.dto.ReviewResponse;

import java.util.List;

public interface ReviewService  {
    ReviewDto createReview(Long productId , ReviewDto reviewDto);

    ReviewResponse  getAllReview(int numberPage, int sizePage ,Long productId);

    ReviewDto updateReview(Long productId ,Long reviewId, ReviewDto reviewDto);

    void deleteReview(Long productId , Long reviewId);
}
