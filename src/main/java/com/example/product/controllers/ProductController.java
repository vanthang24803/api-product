package com.example.product.controllers;

import com.example.product.dto.ProductDto;
import com.example.product.dto.ProductResponse;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "numberPage", defaultValue = "0", required = false) int numberPage,
            @RequestParam(value = "sizePage", defaultValue = "10", required = false) int sizePage
    ) {
        return new ResponseEntity<>(productService.getAllProducts(numberPage, sizePage), HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Not Found !");
        }
    }


    @GetMapping("product")
    public ResponseEntity<List<ProductDto>> getProductByName(
            @RequestParam String name
    ) {
        List<ProductDto> products = productService.getProductByName(name);
        return ResponseEntity.ok(products);
    }

    @PostMapping("product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> createPokemon(
            @RequestBody ProductDto productDto
    ) {
        return new ResponseEntity<>
                (productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<?> updateProduct(
            @RequestBody ProductDto productDto, @PathVariable("id") Long id
    ) {
        try {
            ProductDto respone = productService.updateProduct(productDto, id);
            return ResponseEntity.ok(respone);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Some Thing Went Wrong!");
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("id") Long id
    ) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product delete successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
