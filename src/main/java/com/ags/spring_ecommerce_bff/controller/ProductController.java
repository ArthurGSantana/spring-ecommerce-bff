package com.ags.spring_ecommerce_bff.controller;

import com.ags.spring_ecommerce_bff.dto.request.ProductRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management operations")
public class ProductController {
  private final ProductService productService;

  @GetMapping
  @Operation(
      summary = "Get all products",
      description = "Fetch a list of all products in the system")
  public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
    var products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  @GetMapping("{id}")
  @Operation(summary = "Get product by ID", description = "Fetch a product by their unique ID")
  public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
    var product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }

  @PostMapping
  @Operation(summary = "Create a new product", description = "Register a new product in the system")
  public ResponseEntity<ProductResponseDto> createProduct(
      @Valid @RequestBody ProductRequestDto productDto) {
    var createdProduct = productService.createProduct(productDto);
    return ResponseEntity.ok(createdProduct);
  }

  @PutMapping("{id}")
  @Operation(summary = "Update product by ID", description = "Update an existing product's details")
  public ResponseEntity<ProductResponseDto> updateProduct(
      @PathVariable UUID id, @Valid @RequestBody ProductRequestDto productDto) {
    var updatedProduct = productService.updateProduct(id, productDto);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Delete product by ID", description = "Remove a product from the system")
  public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
