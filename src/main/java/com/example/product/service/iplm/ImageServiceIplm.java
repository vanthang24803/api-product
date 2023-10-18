package com.example.product.service.iplm;

import com.example.product.dto.ImageDto;
import com.example.product.exceptions.ImageNotFoundException;
import com.example.product.exceptions.ProductNotFoundException;
import com.example.product.models.Image;
import com.example.product.models.Product;
import com.example.product.repository.ImageRepository;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageServiceIplm implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageServiceIplm(ImageRepository imageRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ImageDto createImage(Long productId, ImageDto imageDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found")
        );

        Image image = mapToImageEntity(imageDto);
        image.setProduct(product);

        Image savedImage = imageRepository.save(image);
        return mapToImageDto(savedImage);
    }

    @Override
    public List<ImageDto> getAllImagesByProductId(Long productId) {
        List<Image> images = imageRepository.findByProductId(productId);
        List<Image> sortedImages = images.stream()
                .sorted(Comparator.comparing(Image::getId))
                .toList();
        return sortedImages.stream().map(this::mapToImageDto).collect(Collectors.toList());
    }


    @Override
    public ImageDto updateImage(Long productId, Long imageId, ImageDto imageDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found!")
        );

        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> new ImageNotFoundException("Image nor found")
        );

        if (!Objects.equals(image.getProduct().getId(), product.getId())) {
            throw new ImageNotFoundException("This image does not belong to a product");
        }

        modelMapper.map(imageDto, image);
        Image updatedImage = imageRepository.save(image);
        return mapToImageDto(image);
    }

    @Override
    public void deleteImage(Long productId, Long imageId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found!")
        );

        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> new ImageNotFoundException("Image nor found")
        );

        imageRepository.deleteById(imageId);
    }

    private ImageDto mapToImageDto(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }

    private Image mapToImageEntity(ImageDto imageDto) {
        return modelMapper.map(imageDto, Image.class);
    }
}
