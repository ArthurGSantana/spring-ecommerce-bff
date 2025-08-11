package com.ags.spring_ecommerce_bff.controller;

import com.ags.spring_ecommerce_bff.dto.request.ProductRequestDto;
import com.ags.spring_ecommerce_bff.dto.request.ProductRequestFilterDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseFilterDto;
import com.ags.spring_ecommerce_bff.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
  public ResponseEntity<ProductResponseFilterDto<ProductResponseDto>> getAllProductsByFilter(
      @RequestParam(required = false) String sku,
      @RequestParam(required = false) String name,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String sortBy,
      @RequestParam(required = false) String sortOrder) {
    var filter =
        ProductRequestFilterDto.builder()
            .sku(sku)
            .name(name)
            .page(page)
            .size(size)
            .sortBy(sortBy)
            .sortOrder(sortOrder)
            .build();

    var products = productService.getAllProductsByFilter(filter);

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
