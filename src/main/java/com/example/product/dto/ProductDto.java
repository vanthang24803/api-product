package com.example.product.dto;

import com.example.product.models.ECategorize;
import com.example.product.models.ESize;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String decription;
    private Double price = 0.00;

    private ECategorize categorize;
    private  List<ESize> sizes;
    private  List<String> imageUrls;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime upadateAt;

}