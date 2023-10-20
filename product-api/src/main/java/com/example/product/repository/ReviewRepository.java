package com.example.product.repository;

import com.example.product.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review , Long> {
    List<Review> findReviewsByProductId(Long id);
}
