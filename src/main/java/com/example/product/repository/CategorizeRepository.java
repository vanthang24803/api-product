package com.example.product.repository;

import com.example.product.models.Categorize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorizeRepository extends JpaRepository<Categorize , Integer> {
}
