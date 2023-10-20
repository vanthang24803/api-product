package com.example.product.service.iplm;

import com.example.product.dto.ReviewDto;
import com.example.product.dto.ReviewResponse;
import com.example.product.exceptions.ProductNotFoundException;
import com.example.product.exceptions.ReviewNotFoundException;
import com.example.product.models.Product;
import com.example.product.models.Review;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.ReviewRepository;
import com.example.product.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceIplm implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceIplm(ReviewRepository reviewRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewDto createReview(Long productId, ReviewDto reviewDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found!")
        );

        Review review = mapToReviewEntity(reviewDto);
        review.setProduct(product);

        Review savedReview = reviewRepository.save(review);
        return mapToReviewDto(savedReview);
    }

    @Override
    public ReviewResponse getAllReview(int numberPage, int sizePage, Long productId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(numberPage, sizePage, sort);
        Page<Review> reviews = reviewRepository.findAll(pageable);
        List<Review> listOfReviews = reviews.getContent();

        List<ReviewDto> content = listOfReviews.stream().map(this::mapToReviewDto).toList();

        double averageStar = listOfReviews.stream()
                .mapToDouble(Review::getStar)
                .average()
                .orElse(0.00);

        var reviewRespone = new ReviewResponse();
        reviewRespone.setReviews(content);
        reviewRespone.setNumberPage(reviews.getNumber());
        reviewRespone.setSizePage(reviews.getSize());
        reviewRespone.setTotalElement(reviews.getTotalElements());
        reviewRespone.setTotalPage(reviews.getTotalPages());
        reviewRespone.setLast(reviews.isLast());
        reviewRespone.setAverageStarReview((float) averageStar);
        return reviewRespone;
    }

    @Override
    public ReviewDto updateReview(Long productId, Long reviewId, ReviewDto reviewDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found!")
        );
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("Review not found")
        );

        if (!Objects.equals(review.getProduct().getId(), product.getId())) {
            throw new ReviewNotFoundException("This review does not belong to a product");
        }
        if (reviewDto.getCreateAt() == null){
            reviewDto.setCreateAt(review.getCreateAt());
        }
        review.setUpadateAt(LocalDateTime.now());
        modelMapper.map(reviewDto, review);
        Review updatedReview = reviewRepository.save(review);
        return mapToReviewDto(updatedReview);
    }

    @Override
    public void deleteReview(Long productId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("Review not found")
        );

        reviewRepository.deleteById(reviewId);
    }

    private ReviewDto mapToReviewDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    private Review mapToReviewEntity(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }
}
