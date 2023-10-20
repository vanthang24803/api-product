package com.example.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<ProductDto> products;
    private int numberPage;
    private int sizePage;
    private long totalElement;
    private int totalPage;
    private boolean last;
}
