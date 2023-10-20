package com.example.product.service;

import com.example.product.dto.ImageDto;

import java.util.List;

public interface ImageService {

    ImageDto createImage(Long productId , ImageDto imageDto);
    List<ImageDto> getAllImagesByProductId(Long productId);

    ImageDto updateImage(Long productId , Long imageId , ImageDto imageDto);

    void deleteImage(Long productId , Long imageId);
}
