package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    ProductResponse getAllProducts(int numberPage, int sizePage);

    ProductDto getProductById(Long id);

    List<ProductDto> getProductByName(String name);

    ProductDto updateProduct(ProductDto productDto , Long id);

    void deleteProduct(Long id);
}
