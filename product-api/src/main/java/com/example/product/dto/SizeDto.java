package com.example.product.dto;

import com.example.product.models.ESize;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class SizeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private List<ESize> sizeName;
}
