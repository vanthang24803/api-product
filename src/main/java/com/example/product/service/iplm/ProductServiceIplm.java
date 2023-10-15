package com.example.product.service.iplm;

import com.example.product.dto.ProductDto;
import com.example.product.dto.ProductResponse;
import com.example.product.exceptions.ProductNotFoundException;
import com.example.product.models.Product;
import com.example.product.repository.*;
import com.example.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceIplm implements ProductService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final CategorizeRepository categorizeRepository;
    private final SizeRepository sizeRepository;

    @Autowired
    public ProductServiceIplm(ProductRepository productRepository, ReviewRepository reviewRepository,
                              ImageRepository imageRepository, CategorizeRepository categorizeRepository,
                              SizeRepository sizeRepository, ModelMapper modelMapper)
    {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.imageRepository = imageRepository;
        this.categorizeRepository = categorizeRepository;
        this.sizeRepository = sizeRepository;
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        try {
            Product product = modelMapper.map(productDto, Product.class);
            Product newProduct = productRepository.save(product);
            return modelMapper.map(newProduct, ProductDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating product", e);
        }
    }

    @Override
    public ProductResponse getAllProducts(int numberPage, int sizePage) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(numberPage, sizePage, sort);
        Page<Product> products = productRepository.findAll(pageable);
        List<Product> listOfProducts = products.getContent();

        List<ProductDto> content = listOfProducts.stream().map(product -> {
            ProductDto productDto = mapToProductDto(product);

            return productDto;
        }).collect(Collectors.toList());

        var productResponse = new ProductResponse();
        productResponse.setProducts(content);
        productResponse.setNumberPage(products.getNumber());
        productResponse.setSizePage(products.getSize());
        productResponse.setTotalElement(products.getTotalElements());
        productResponse.setTotalPage(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow
                        (() -> new ProductNotFoundException("Product Not Found"));
        return mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getProductByName(String name) {
        List<Product> products = productRepository.findByName(name);
        return products.stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product Not Found!")
        );

        // If createAt in productDto is null, set it to the current value of createAt in product
        if (productDto.getCreateAt() == null) {
            productDto.setCreateAt(product.getCreateAt());
        }

        modelMapper.map(productDto, product);
        product.setUpadateAt(LocalDateTime.now());
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product Not Found!")
        );

        productRepository.delete(product);
    }


    private ProductDto mapToProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product mapToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

}